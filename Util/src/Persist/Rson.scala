/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Helper class for constructing [[String]]s from ShowT implicit instances on objects. */
class Rval(val str: String) extends AnyVal
{ /** Adds any object' with an implicit instance in scope's String representation to this Rval's String. */
  def - [A](value: A)(implicit ev: ShowDecT[A]): Rval = new Rval(str + "\n" + ev.strT(value) + ";")
}

object Rval
{ /** Factory apply method for creating initial Rval box. */
  def apply[A](value: A)(implicit ev: PersistDec[A]): Rval = new Rval(ev.strT(value) + ";")
}

/** Class for creating RSOn settings. */
class Setting(val str: String) extends AnyVal
{
  /** Not sure why this method is called ap. */
  def ap[A](setting: String, value: A)(implicit ev: PersistDec[A]): Setting =
  {
    new Setting(str + "\n" + setting + " = " + ev.strT(value) + ";")
  }
}

object Setting
{
  def apply[A](setting: String, value: A)(implicit ev: ShowDecT[A]): Setting = new Setting(setting + " = " + ev.strT(value) + ";")
}

