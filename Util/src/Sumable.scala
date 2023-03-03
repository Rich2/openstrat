/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A type class for a sum like operation */
trait Sumable[A]
{ def identity: A
  def sum(a1: A, a2: A): A
}

object Sumable
{
  val intImplicit: Sumable[Int] = new Sumable[Int]
  { override val identity: Int = 0
    override def sum(a1: Int, a2: Int): Int = a1 + a2
  }
}