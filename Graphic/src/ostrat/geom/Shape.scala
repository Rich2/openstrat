/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** Shape is an Array[Double] based collection for a sequence of CurveSegs, similar to a Polygon which is an Array[Double based collection of just
 *   LineSegs. It Uses 6 Doubles for each CurveSeg. The first Double of each curveSeg is set to Negative Infinity for a LineSeg positive infinity for
 *   an ArcSeg, but represents the x component of the first control point for a BezierSeg. */
class Shape(val array: Array[Double]) extends AnyVal with ArrProdDbl7[CurveSeg] with Transer
{ type ThisT = Shape
  def unsafeFromArray(array: Array[Double]): Shape = new Shape(array)
  override def typeStr = "Shape"

  override def newElem(iMatch: Double, d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): CurveSeg =
    new CurveSeg(iMatch, d1, d2, d3, d4, d5, d6)
   
  def fTrans(f: Vec2 => Vec2): Shape =
  { val newArray = new Array[Double](length * 7)
    def setMiddle(offset: Int): Unit =
    { val newMiddle: Vec2 = f(array(offset + 3) vv array(offset + 4))
      newArray(offset + 3) = newMiddle.x
      newArray(offset + 4) = newMiddle.y
    }
      
    def setEnd(offset: Int): Unit =
    { val newEnd: Vec2 = f(array(offset + 5) vv array(offset + 6))
      newArray(offset + 5) = newEnd.x
      newArray(offset + 6) = newEnd.y
    }      
      
    (0 until length).foreach{index =>
      val offset = index * 7
      array(offset) match
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
          val newControl1: Vec2 = f(array(offset + 1) vv array(offset + 2)) 
          newArray(offset + 1) = newControl1.x
          newArray(offset + 2) = newControl1.y
          setMiddle(offset)
          setEnd(offset)
        }
        
        case n => excep("iMatch in LineSeg has value: " + n.toString + " Must be 10, 11 0r 12.")
      }
    }
    new Shape(newArray)
  }
   
  def fill(colour: Colour): ShapeFill = ShapeFill(this, colour)
  def draw(lineWidth: Double, lineColour: Colour = Black) = ShapeDraw(this,lineWidth, lineColour)
  def fillDraw(fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) = ShapeFillDraw(this, fillColour, lineWidth, lineColour)
  def fillDrawClick(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black): List[GraphicElem] =
    List(ShapeFillDraw(this, fillColour, lineWidth, lineColour), ShapeActive(this, evObj))
       
  def fillSlateable(colour: Colour, evObj: AnyRef, posn: Vec2 = Vec2Z): UnScaledShape = UnScaledShape(posn, this, evObj, Arr(ShapeFill(this, colour)))
  def fillScale(colour: Colour, factor: Double): ShapeFill = ShapeFill(this.scale(factor), colour)
  def fillScaleSlate(colour: Colour, factor: Double, offset: Vec2): ShapeFill = ShapeFill(this.scale(factor).slate(offset), colour)
   
  /** Not sure if this method should be a member of Transable */
  def boundingRect =
  { //val t = Arc()
    var minX, maxX, minY, maxY = 0.0
    var i = 0
    this.foreach {ss =>
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
    BoundingRect(minX, maxX, minY, maxY)               
  }
  def ptInShape: Vec2 => Boolean = pt =>  pMap[Vec2, Polygon](_.pEnd).ptInPolygon(pt) 
   
  /** Not sure if this is useful */   
  def segForeach(fLineSeg: CurveSeg => Unit, fArcSeg: CurveSeg => Unit, fBezierSeg: CurveSeg => Unit): Unit = 
    foreach(_.segDo(fLineSeg, fArcSeg, fBezierSeg))         
}

object Shape extends ProductD7sCompanion[CurveSeg, Shape]
{
   implicit val factory: Int => Shape = i => new Shape(new Array[Double](i * 7))   
}
