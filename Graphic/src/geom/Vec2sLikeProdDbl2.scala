/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A common trait for all classes that can be defined by a sequence of Vec2s. */
trait Vec2sLike extends TransElem
{
  def apply(index: Int): Vec2
  def foreach[U](f: Vec2 => U): Unit
  def foreachTail[U](f: Vec2 => U): Unit

  def foldLeft[B](initial: B)(f: (B, Vec2) => B): B =
  { var acc: B = initial
    foreach{ v => acc = f(acc, v) }
    acc
  }
}

/** The purpose of this trait is to provide the helper method for Vec2 transformations. */
trait Vec2sLikeProdDbl2 extends Vec2sLike with ArrProdDbl2[Vec2]
{ def arrTrans(f: Vec2 => Vec2): Array[Double] =
  { val newArray = new Array[Double](arrayUnsafe.length)
    var count = 0
    while (count < arrayUnsafe.length)
    {
      val newVec = f(arrayUnsafe(count) vv arrayUnsafe(count + 1))
      newArray(count) = newVec.x
      newArray(count + 1) = newVec.y
      count += 2
    }
    newArray
  }
  final override def elemBuilder(d1: Double, d2: Double): Vec2 = Vec2.apply(d1, d2)
  override def foldLeft[B](initial: B)(f: (B, ostrat.geom.Vec2) => B): B = super.foldLeft(initial)(f)
  //def foreach(f: Vec2 => Unit): Unit = { var count = 0; while(count < length) { f(apply(count)); count += 1 } }
}