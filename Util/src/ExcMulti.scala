/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.targetName

trait ThrowMulti extends Throwable
{ /** Member errors. */
  def mems: RArr[Throwable]

  @targetName("append") def ++ (operand: ThrowMulti): ThrowMulti = ThrowMulti(mems ++ operand.mems)
}

object ThrowMulti
{
  def apply(throws: RArr[Throwable]): ThrowMulti = ThrowMultiGen(throws)

  def apply(throws: Throwable*): ThrowMulti = ThrowMultiGen(throws.toRArr)
  case class ThrowMultiGen(mems: RArr[Throwable]) extends ThrowMulti

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

trait ExcMulti extends Exception, ThrowMulti
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
