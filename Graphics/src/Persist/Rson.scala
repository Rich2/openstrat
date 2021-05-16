/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Helper class for constructing [[String]]s from ShowT implicit instances on objects. */
class Rval(val str: String) extends AnyVal
{ /** Adds any object' with an implicit instance in scope's String representation to this Rval's String. */
  def - [A](value: A)(implicit ev: ShowT[A]): Rval = new Rval(str + "\n" + ev.strT(value) + ";")
}

object Rval
{ /** Factory apply method for creating initial Rval box. */
  def apply[A](value: A)(implicit ev: Persist[A]): Rval = new Rval(ev.strT(value) + ";")
}

class Sett(val str: String) extends AnyVal
{
  /** Not sure why this method is called ap. */
  def ap[A](setting: String, value: A)(implicit ev: Persist[A]): Sett =
  {
    new Sett(str + "\n" + setting + " = " + ev.strT(value) + ";")
  }
}

object Sett
{
  def apply[A](setting: String, value: A)(implicit ev: Persist[A]): Sett = new Sett(setting + " = " + ev.strT(value) + ";")
}

