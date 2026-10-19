/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** An error from Rich Script Object Notation. */
trait RsonException extends Exception

/** Compound error trait for errors extending [[RsonException]]. */
trait RsonExcMulti[E <: RsonException] extends ExcMulti[E], RsonException
{ override def getMessage: String = s"$numErrs errors of type RsonException"
  override def toString: String = "RsonExcMulti" + s"$numErrs errors".enParenth
}

object RsonExcMulti
{ /** Factory apply method to construct [[RsonExcMulti]] from [[RArr]] of errors subclassing [[RsonException]]. */
  def apply[E <: RsonException](exceps: RArr[E]): RsonExcMulti[E] = RsonExcMultiGen(exceps)

  /** Factory apply method to construct [[RsonExcMulti]] from repeat parameter errors subclassing [[RsonException]]. */
  def apply[E <: RsonException](errors: E*)(using ClassTag[E]): RsonExcMulti[E] = RsonExcMultiGen(errors.toRArr)

  /** Factory method to construct [[Left]] of [[RsonExcMulti]] of errors subclassing [[RsonException]]. */
  def left[E <: RsonException](input: Either[E, ?]*)(using ClassTag[E]): Left[RsonExcMulti[E], Nothing] =
  { val buff: ArrayBuffer[E] = new ArrayBuffer[E]()
    input.foreach{
      case Left(err) => buff.append(err)
      case _ =>
    }
    Left(RsonExcMultiGen(buff.toRArr))
  }

  /** Implementation class for the general case of [[ExcMulti]]. */
  case class RsonExcMultiGen[E <: RsonException](mems: RArr[E]) extends RsonExcMulti[E]
}

/** An error from Rich Script Object Notation. */
trait SettFailException extends Exception

/** Compound error trait for errors extending [[SettFailException]]. */
trait SettFailExcMulti[E <: SettFailException] extends ExcMulti[E], SettFailException
{ override def getMessage: String = s"$numErrs errors of type SettFailException"
  override def toString: String = "SettFailExcMulti" + s"$numErrs errors".enParenth
}

object SettFailExcMulti
{ /** Factory apply method to construct [[SettFailExcMulti]] from [[RArr]] of errors subclassing [[SettFailException]]. */
  def apply[E <: SettFailException](exceps: RArr[E]): SettFailExcMulti[E] = SettFailExcMultiGen(exceps)

  /** Factory apply method to construct [[SettFailExcMulti]] from repeat parameter errors subclassing [[SettFailException]]. */
  def apply[E <: SettFailException](errors: E*)(using ClassTag[E]): SettFailExcMulti[E] = SettFailExcMultiGen(errors.toRArr)

  /** Factory method to construct [[Left]] of [[SettFailExcMulti]] of errors subclassing [[SettFailException]]. */
  def left[E <: SettFailException](input: Either[E, ?]*)(using ClassTag[E]): Left[SettFailExcMulti[E], Nothing] =
  { val buff: ArrayBuffer[E] = new ArrayBuffer[E]()
    input.foreach{
      case Left(err) => buff.append(err)
      case _ =>
    }
    Left(SettFailExcMultiGen(buff.toRArr))
  }

  /** Implementation class for the general case of [[ExcMulti]]. */
  case class SettFailExcMultiGen[E <: SettFailException](mems: RArr[E]) extends SettFailExcMulti[E]
}

case class SettingNotFound(settName: String) extends Exception, SettFailException
{
  override def getMessage: String = s"$settName setting not found."
}

/** Helper class for constructing [[String]]s from ShowT implicit instances on objects. */
class Rval(val str: String) extends AnyVal
{ /** Adds any object' with an implicit instance in scope's String representation to this Rval's String. */
  def -[A](value: A)(using ev: Show[A]): Rval = new Rval(str + "\n" + ev.strT(value) + ";")
}

object Rval
{ /** Factory apply method for creating initial Rval box. */
  def apply[A](value: A)(implicit ev: Show[A]): Rval = new Rval(ev.strT(value) + ";")
}

/** Class for creating RSOn settings. */
class Setting(val str: String) extends AnyVal
{
  /** Not sure why this method is called ap. */
  def ap[A](setting: String, value: A)(implicit ev: Show[A]): Setting =
  {
    new Setting(str + "\n" + setting + " = " + ev.strT(value) + ";")
  }
}

object Setting
{
  def apply[A](setting: String, value: A)(implicit ev: Show[A]): Setting = new Setting(setting + " = " + ev.strT(value) + ";")
}