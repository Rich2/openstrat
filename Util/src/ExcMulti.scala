/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.targetName, reflect.ClassTag

/** An [[Exception]] that is concatenation of multiple errors. */
trait ErrMulti[+E]// extends Exception
{ /** Member errors. */
  def mems: RArr[E]
  
  /** The number of Errors in this multiple error */
  def numErrs: Int = mems.length

  @targetName("append")def ++[EE >: E] (operand: ErrMulti[EE])(using build: ErrBuilderOld[EE], ctE: ClassTag[EE]): ErrMulti[EE] =
    build.multi(mems ++ operand.mems)

  
}

/** An error that is concatenation of multiple [[Throwable]]s. */
trait ThrowMulti[E <: Throwable] extends Throwable, ErrMulti[E]
{  override def mems: RArr[E]
}

object ThrowMulti
{
  def apply[E <: Throwable](throws: RArr[E]): ThrowMulti[E] = ThrowMultiGen(throws)

  def apply[E <: Throwable](throws: E*)(using ClassTag[E]): ThrowMulti[E] = ThrowMultiGen(throws.toRArr)

  given multiBuilder[E <: Throwable]: ErrMultiBuilder[Throwable, E, ThrowMulti[E]] = new ErrMultiBuilder[Throwable, E, ThrowMulti[E]]
  { override def multi(arr: RArr[E]): ThrowMulti[E] = ThrowMultiGen(arr)
    override def multi(errs: E*)(using ct: ClassTag[E]): ThrowMulti[E] = ThrowMultiGen(errs.toRArr)
  }
  
  case class ThrowMultiGen[E <: Throwable](mems: RArr[E]) extends Exception, ThrowMulti[E]
}

trait ErrMultiBuilder[EE, E <: EE, ME <: ErrMulti[E] & EE]
{ def multi(arr: RArr[E]): ME
  def multi(errs: E*)(using ct: ClassTag[E]): ME
}

object ErrMultiBuilder
{
  given excMultiEv[E <: Exception]: ErrMultiBuilder[Exception, E, ExcMulti[E]] = new ErrMultiBuilder[Exception, E, ExcMulti[E]]
  { override def multi(arr: RArr[E]): ExcMulti[E] = ExcMulti(arr)
    override def multi(errs: E*)(using ct: ClassTag[E]): ExcMulti[E] = ExcMulti(errs.toRArr)
  }
}

/** Not sure if this is needed. */
trait ErrBuilderOld[E]
{
  def multi(mems: RArr[E]): ErrMulti[E] & E
}

/** An error that is concatenation of multiple [[Exception]]s. */
trait ExcMulti[E <: Exception] extends Exception, ThrowMulti[E]
{
  override def mems: RArr[E]

  @targetName("append") def ++[EE >: E <: Exception](operand: ExcMulti[EE])(using ClassTag[EE]): ExcMulti[EE] =
  { val rArr: RArr[EE] = mems ++ operand.mems
    ExcMulti[EE](rArr)
  }

  override def getMessage: String = s"$numErrs errors"
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