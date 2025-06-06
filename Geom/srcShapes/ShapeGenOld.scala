/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black

/** The generalised implementation of a [[Shape]]. A closed sequence of curve segments. An Array[Double] based collection for a sequence of CurveSegs, similar
 * to a Polygon which is an Array[Double based collection of just LineSegs. It Uses 6 Doubles for each CurveSeg. The first Double of each curveSeg is set to
 * Negative Infinity for a LineSeg positive infinity for an ArcSeg, but represents the x component of the first control point for a BezierSeg. */
class ShapeGenOld(val arrayUnsafe: Array[Double]) extends SeqSpecDbl7[CurveTailOld], AffinePreserve
{ type ThisT = ShapeGenOld
  def fromArray(array: Array[Double]): ShapeGenOld = new ShapeGenOld(array)
  override def typeStr = "Shape"
  override def fElemStr: CurveTailOld => String = _.toString

  override def elemFromDbls(iMatch: Double, d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): CurveTailOld =
    CurveTailOld(iMatch, d1, d2, d3, d4, d5, d6)

  def ptsTrans(f: Pt2 => Pt2): ShapeGenOld =
  { val newArray = new Array[Double](numElems * 7)
    def setMiddle(offset: Int): Unit =
    { val pt = Pt2(arrayUnsafe(offset + 3), arrayUnsafe(offset + 4))
      val newMiddle: Pt2 = f(pt)
      newArray(offset + 3) = newMiddle.x
      newArray(offset + 4) = newMiddle.y
    }

    def setEnd(offset: Int): Unit =
    { 
      val pt = Pt2(arrayUnsafe(offset + 5), arrayUnsafe(offset + 6))
      val newEnd: Pt2 = f(pt)
      newArray(offset + 5) = newEnd.x
      newArray(offset + 6) = newEnd.y
    }

    (0 until numElems).foreach{ index =>
      val offset = index * 7
      arrayUnsafe(offset) match
      {
        case 10 =>
        { newArray(offset) = 10
          setEnd(offset)
        }

        case 11 =>
        { newArray(offset) = 11
          setMiddle(offset)
          setEnd(offset)
        }

        case 12 =>
        { newArray(offset) = 12
          val pt = Pt2(arrayUnsafe(offset + 1), arrayUnsafe(offset + 2))
          val newControl1: Pt2 = f(pt)
          newArray(offset + 1) = newControl1.x
          newArray(offset + 2) = newControl1.y
          setMiddle(offset)
          setEnd(offset)
        }

        case n => excep("iMatch in LineSeg has value: " + n.toString + " Must be 10, 11 0r 12.")
      }
    }
    new ShapeGenOld(newArray)
  }

  def fill(colour: Colour): PolyCurveFill = PolyCurveFill(this, colour)
  def draw(lineColour: Colour = Black, lineWidth: Double = 2.0): PolyCurveDraw = PolyCurveDraw(this, lineColour, lineWidth)

  def shapeAll(shape: ShapeGenOld, evObj: AnyRef, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2, lineColour: Colour = Black):
    PolyCurveAllOld = PolyCurveAllOld(shape, evObj, str, fillColour, fontSize, lineColour, lineWidth)

  def fillScale(colour: Colour, factor: Double): PolyCurveFill = PolyCurveFill(this.scale(factor), colour)
  def fillScaleSlate(colour: Colour, factor: Double, offset: Pt2): PolyCurveFill = PolyCurveFill(this.scale(factor).slate(offset), colour)

  /** Not sure if this method should be a member of Transable */
  def boundingRect: Rect =
  { //val t = Arc()
    var minX, maxX, minY, maxY = 0.0
    var i = 0
    foreach { ss =>
      val v = ss.pEnd
      if (i == 0)
      { minX = v.x
        maxX = v.x
        minY = v.y
        maxY = v.y
      }
      else
      { minX = minX.min(v.x)
        maxX = maxX.max(v.x)
        minY = minY.min(v.y)
        maxY = maxY.max(v.y)
      }
      i += 1
    }
    if (i == 0) throw new Exception("boundingRect method called on empty Vec2 collection") else {}
    Rect.lrbt(minX, maxX, minY, maxY)
  }
  def ptInShape: Pt2 => Boolean = pt => {
    val po: Polygon = this.mapPolygon/*dataMap[Pt2, PolygonGen]*/(_.pEnd)
      po.ptInside(pt)
  }

  /** Not sure if this is useful */
  def tailForeach(fLineSeg: CurveTailOld => Unit, fArcSeg: CurveTailOld => Unit, fBezierSeg: CurveTailOld => Unit): Unit =
    foreach(_.segDo(fLineSeg, fArcSeg, fBezierSeg))

  @inline def segLast: CurveTailOld = last
}

object ShapeGenOld extends CompanionSlDbl7[CurveTailOld, ShapeGenOld]
{ /** Method to create the final object from the backing Array[Double]. End users should rarely have to use this method. */
  override def fromArray(array: Array[Double]): ShapeGenOld = new ShapeGenOld(array)
}