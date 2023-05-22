/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq

/** Not sure about this trait for occupants of a hex tile. */
trait SqMem[A]
{ val sqCen: SqCen
  val value: A
}

object SqMem
{
  def apply[A](sc: SqCen, value: A): SqMem[A] = SqMemImp[A](sc, value)
  case class SqMemImp[A](sqCen: SqCen, value: A) extends SqMem[A]
}

trait SqMemShow[A] extends SqMem[A] with Show2[SqCen, A]
{ override def show1: SqCen = sqCen
  override def name1: String = "SqCen"
  override implicit def showT1: ShowT[SqCen] = SqCen.persistEv
  override def show2: A = value
}