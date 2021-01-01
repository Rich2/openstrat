/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

class Rval(val str: String) extends AnyVal
{
  def - [A](value: A)(implicit ev: Persist[A]): Rval = new Rval(str + "\n" + ev.showT(value, 0) + ";")
}

object Rval
{
  def apply[A](value: A)(implicit ev: Persist[A]): Rval = new Rval(ev.showT(value, 0) + ";")
}

class Sett(val str: String) extends AnyVal
{
  /** Not sure why this method is called ap. */
  def ap[A](setting: String, value: A)(implicit ev: Persist[A]): Sett =
  {
    new Sett(str + "\n" + setting + " = " + ev.showT(value, 0) + ";")
  }
}

object Sett
{
  def apply[A](setting: String, value: A)(implicit ev: Persist[A]): Sett = new Sett(setting + " = " + ev.showT(value, 0) + ";")
}

