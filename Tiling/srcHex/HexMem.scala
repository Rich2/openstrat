/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Not sure about this trait for occupants of a hex tile. */
trait HexMem[A]
{ val hc: HCen
  val value: A
}

object HexMem
{
  def apply[A](hc: HCen, value: A): HexMem[A] = HexMemImp[A](hc, value)
  case class HexMemImp[A](hc: HCen, value: A) extends HexMem[A]
}

trait HexMemShow[A] extends HexMem[A] with Tell2[HCen, A]
{ override def tell1: HCen = hc
  override def name1: String = "hCen"
  override implicit def show1: Show[HCen] = HCen.persistEv
  override def tell2: A = value
}