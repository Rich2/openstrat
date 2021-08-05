/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Array[Double] based collection class for a LinePath. Conversion to and from the Vec2s class and Polygon class should not entail a runtime
 *  cost. */
class LinePath(val arrayUnsafe: Array[Double]) extends AffinePreserve with Pt2sLike with Dbl2sLinePath[Pt2]
{ type ThisT = LinePath
  def unsafeFromArray(array: Array[Double]): LinePath = new LinePath(array)
  override def typeStr: String = "LinePath"
  
  @inline def lengthFull: Int = arrayUnsafe.length / 2
  @inline def xStart: Double = arrayUnsafe(0)
  @inline def yStart: Double = arrayUnsafe(1)
  @inline def pStart: Pt2 = Pt2(xStart, yStart)
 
  def ptsTrans(f: Pt2 => Pt2): LinePath =  new LinePath(arrTrans(f))
  
  def foreachEnd(f: (Double, Double) => Unit): Unit =
  { var count = 1
    while (count < lengthFull)
    { f(arrayUnsafe(count *2), arrayUnsafe( count * 2 + 1))
      count += 1      
    }
  }
  
  def draw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinePathDraw = LinePathDraw(this, lineWidth, colour)
}

/** Companion object for LinePath contains apply factory object and Persist type class instance. */
object LinePath extends Dbl2sDataCompanion[Pt2, LinePath]
{ override def fromArrayDbl(array: Array[Double]): LinePath = new LinePath(array)

  implicit val persistImplicit: Dbl2sDataPersist[Pt2, LinePath] = new Dbl2sDataPersist[Pt2, LinePath]("LinePath")
  { override def fromArray(value: Array[Double]): LinePath = new LinePath(value)
  }
}