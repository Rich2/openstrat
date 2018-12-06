/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

class Rval(val str: String) extends AnyVal
{
  def - [A](value: A)(implicit ev: Persist[A]): Rval = new Rval(str + "\n" + ev.persist(value) + ";")
}

object Rval
{
  def apply[A](value: A)(implicit ev: Persist[A]): Rval = new Rval(ev.persist(value) + ";")
}

class Sett(val str: String) extends AnyVal
{
  def ap[A](settSym: Symbol, value: A)(implicit ev: Persist[A]): Sett =
  {
    new Sett(str + "\n" + settSym.name + " = " + ev.persist(value) + ";")
  }
}

object Sett
{
  def apply[A](settSym: Symbol, value: A)(implicit ev: Persist[A]): Sett = new Sett(settSym.name + " = " + ev.persist(value) + ";")
}

