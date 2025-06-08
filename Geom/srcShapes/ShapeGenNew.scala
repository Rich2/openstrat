/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait ShapeSegsNew
{
  def foreach(f: CurveTail => Unit): Unit
}

/** Yet another attempt at a shapeGen class. */
class ShapeGenNew(val arrayUnsafe: Array[Double]) extends ShapeSegsNew
{
  def numSegs: Int = arrayUnsafe.length / 10
  override def foreach(f: CurveTail => Unit): Unit = iUntilForeach(numSegs){ i =>
    val ii = i * 10
    arrayUnsafe(ii) match
    {
      case 10 => Pt2(arrayUnsafe(ii + 1), arrayUnsafe(ii + 2))

      case 12 => ??? // CArcTail()
  
    }
  }
}
