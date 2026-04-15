/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.targetName, reflect.ClassTag

/** An error that is concatenation of multiple errors. */
trait ErrMulti[+E]
{ /** Member errors. */
  def mems: RArr[E]

  @targetName("append") def ++[EE >: E] (operand: ErrMulti[EE])(using build: ErrBuilder[EE], ctE: ClassTag[EE]): ErrMulti[EE] = build.multi(mems ++ operand.mems)
}

case class ThrowMulti(mems: RArr[Throwable]) extends Throwable, ErrMulti[Throwable]

object ThrowMulti
{
  def apply(throws: RArr[Throwable]): ThrowMulti = new ThrowMulti(throws)

  def apply(throws: Throwable*): ThrowMulti = new ThrowMulti(throws.toRArr)
}

trait ErrBuilder[E]
{
  def multi(mems: RArr[E]): ErrMulti[E] & E
}

trait ExcMulti extends Exception, ErrMulti[Exception]
{
  override def mems: RArr[Exception]

  @targetName("append") def ++ (operand: ExcMulti): ExcMulti = ExcMulti(mems ++ operand.mems)
}

object ExcMulti
{
  def apply(exceps: RArr[Exception]): ExcMulti = ExcMultiGen(exceps)

  def apply(throws: Exception*): ExcMulti = ExcMultiGen(throws.toRArr)
  case class ExcMultiGen(mems: RArr[Exception]) extends ExcMulti
}

extension (thisExcep: Exception)
{
  def combine(operand: Exception): ExcMulti = thisExcep match
  {  case multi1: ExcMulti => operand match
    { case multi2: ExcMulti => ExcMulti(multi1.mems ++ multi2.mems)
      case excep2 => ExcMulti(multi1.mems +% excep2)
    }
    case excep1 => operand match
    { case multi2: ExcMulti => ExcMulti(excep1 %: multi2.mems)
      case excep2 => ExcMulti(RArr(excep1, excep2))
    }
  }
}


object ExcBi
{
  def map2[E <: Exception, A1, A2, B](eb1: ErrBi[E, A1], eb2: ErrBi[E, A2])(f: (A1, A2) => B): ErrBi[Exception, B] = eb1 match
  { case Succ(a1) => eb2.map(a2 => f(a1, a2))
    case f1: Fail[E] => eb2 match
    { case Fail(err2) => Fail(ExcMulti(f1.error, err2))
      case _ => f1
    }
    case _ => excep("Unforseen match case.")
  }
}
