/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.targetName, reflect.ClassTag

/** An [[Exception]] that is concatenation of multiple errors. */
trait ErrMulti[+E] extends Exception
{ /** Member errors. */
  def mems: RArr[E]
  
  /** The number of Errors in this multiple erroor */
  def numErrs: Int = mems.length

  @targetName("append")def ++[EE >: E] (operand: ErrMulti[EE])(using build: ErrBuilder[EE], ctE: ClassTag[EE]): ErrMulti[EE] =
    build.multi(mems ++ operand.mems)

  override def getMessage: String = s"$numErrs errors" 
}

/** An error that is concatenation of multiple [[Throwable]]s. */
trait ThrowMulti[E <: Throwable] extends ErrMulti[Throwable]
{  override def mems: RArr[Throwable]
}
object ThrowMulti
{
  def apply[E <: Throwable](throws: RArr[E]): ThrowMulti[E] = ThrowMultiGen(throws)

  def apply[E <: Throwable](throws: E*)(using ClassTag[E]): ThrowMulti[E] = ThrowMultiGen(throws.toRArr)
  
  case class ThrowMultiGen[E <: Throwable](mems: RArr[E]) extends Exception, ThrowMulti[E]
}

trait ErrBuilder[E]
{
  def multi(mems: RArr[E]): ErrMulti[E] & E
}

/** An error that is concatenation of multiple [[Exception]]s. */
trait ExcMulti[E <: Exception] extends Exception, ErrMulti[E]
{
  override def mems: RArr[E]

  @targetName("append") def ++[EE >: E <: Exception](operand: ExcMulti[EE])(using ClassTag[EE]): ExcMulti[EE] =
  { val rArr: RArr[EE] = mems ++ operand.mems
    ExcMulti[EE](rArr)
  }
}

object ExcMulti
{
  def apply[E <: Exception](exceps: RArr[E]): ExcMulti[E] = ExcMultiGen(exceps)

  def apply[E <: Exception](errors: E*)(using ClassTag[E]): ExcMulti[E] = ExcMultiGen(errors.toRArr)

  def unapply[E <: Exception](inp: Any): Option[RArr[E]] = inp match
  { case inp: ExcMulti[E] => Some(inp.mems)
    case _ => None
  }
  
  /** Implementation class for the general case of [[ExcMulti]]. */
  case class ExcMultiGen[E <: Exception](mems: RArr[E]) extends ExcMulti[E]  
}

extension[E <: Exception](thisExcep: E)
{
  def combine(operand: Exception) = thisExcep match
  {  case ExcMulti(mems1) => operand match
    { case ExcMulti(mems2) => ExcMulti[Exception](mems1 ++ mems2.asInstanceOf[RArr[Exception]])
      case excep2 => ExcMulti[Exception](mems1 +% excep2)
    }
    case excep1 => operand match
    { case ExcMulti(mems2) => ExcMulti(excep1 %: mems2.asInstanceOf[RArr[Exception]])
      case excep2 => ExcMulti(RArr(excep1, excep2))
    }
  }
}


object ExcBi
{
  def map2[E <: Exception, A1, A2, B](eb1: Either[E, A1], eb2: Either[E, A2])(f: (A1, A2) => B)(using ctE: ClassTag[E]): Either[Exception, B] = eb1 match
  { case Right(a1) => eb2.map(a2 => f(a1, a2))
    case Left(err1) => eb2 match
    { case Left(err2) => Left(ExcMulti(err1, err2))
      case _ => Left(err1)
    }
  }
}