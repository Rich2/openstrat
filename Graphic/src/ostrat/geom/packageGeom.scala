/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import math._, Colour.Black

/** I chose the package name to not clash with "geometry" that may be use in other libraries This package contains Basic geometry. A number of
 *   implementation Value classes of the Int and Double product classes defined in ostrat. 2d graphical objects for generalised use. They are of 
 *   particular use for the generic canvas based classes defined in pCanv but can be used in any display framework and for printing. */
package object geom
{
  implicit def TransToExtension[T](value: T)(implicit ev: Trans[T]) = new TransExtension[T](value, ev)
  implicit def noScaleTransExtension[T <: UnScaled](value: T)(implicit ev: Trans[T#TranserT]) = new TransExtension[T#TranserT](value.scaled, ev)
  /** Vec2(x = 0, y = 0) constant */
  val Vec2Z = Vec2(0, 0)
  /** Dist2(0.km, 0.km) constant */
  val Dist2Z = Dist2(0.km, 0.km)
  val LongD = 2.0 / Cos30
  val cos30: Double = cos(Pi / 6)
  val LatLong0 = LatLong(0, 0)
  val EarthPolarRadius: Dist = 6356.7523.km
  val EarthEquatorialRadius: Dist = 6378.137.km
  val EarthAvDiameter: Dist = 12742.km
  val EarthAvRadius: Dist = EarthAvDiameter / 2
  type SSet[A] = scala.collection.SortedSet[A]
  type GraphicElems = Arr[GraphicElem]
  /** Hopefully this existential syntax baggage will be gone in dotty */
  type CanvO = GraphicElem
  implicit def intToImplicitGeom(thisInt: Int): IntGeomImplicit = new IntGeomImplicit(thisInt)           
  implicit def doubleToImplicitGeom(thisDouble: Double): DoubleImplicitGeom = new DoubleImplicitGeom(thisDouble)
 
  implicit class StringImplictGeom(thisString: String)
  { import pParse.{ stringToStatements => stss}
    def findVec2: EMon[Vec2] = stss(thisString).flatMap(_.findType[Vec2])
    def findVec2Else(elseValue: => Vec2) = findVec2.getElse(elseValue)
    def findVec2Sett(setting: String): EMon[Vec2] = stss(thisString).flatMap(_.findSett[Vec2](setting))
    def findVec2SettElse(setting: String, elseValue: Vec2): Vec2 = findVec2Sett(setting).getElse(elseValue)

    def graphic(fontSize: Int = 24, posn: Vec2 = Vec2Z, colour: Colour = Black, align: TextAlign = CenAlign,
      baseLine: BaseLine = BaseLine.Alphabetic): TextGraphic = TextGraphic(thisString, fontSize, posn, colour, align, baseLine)
  }

  implicit class DoubleImplicit(thisDouble: Double)
  { def * (operand: Vec2): Vec2 = new Vec2(thisDouble * operand.x, thisDouble * operand.y)
    def metres: Dist = new Dist(thisDouble)
  }

  implicit class DistImplicit(thisDist: Dist)
  {  def / (operand: Dist): Double = thisDist.metres / operand.metres
  }
   
  implicit class OptionGeomImplicit[A](thisOption: Option[A])
  {  def canvObjsPair(f: A => (Seq[GraphicElem], Seq[GraphicElem])): (Seq[GraphicElem], Seq[GraphicElem]) = thisOption match
     {
        case Some(a) => f(a)
        case None => (Seq(), Seq())
     }
  }
   
  /** 0 degrees or 0 radians */
  def deg0: Angle = Angle(0)
  /** 30 degrees anti-clockwise or + Pi/6 radians */
  val deg30: Angle = Angle(Pi / 6)
  /** 36 degrees anti-clockwise or + Pi/5 radians */
  val deg36: Angle = Angle(Pi / 5)
  /** 45 degrees anti-clockwise or + Pi/4 radians */
  val deg45: Angle = Angle(Pi / 4)
  /** 60 degrees anti-clockwise or + Pi/3 radians */
  val deg60: Angle  = Angle(Pi / 3)  
  /** 72 degrees anti-clockwise or + Pi2/5 radians */
  val deg72: Angle = Angle(Pi2 / 5)
  /** 90 degrees anti-clockwise or + Pi/2 radians */
  val deg90: Angle = Angle(Pi / 2)  
  /** 120 degrees anti-clockwise or + 2 * Pi/3 radians */
  val deg120: Angle = Angle(2 * Pi / 3)
  /** 135 degrees anti-clockwise or + 3 * Pi/4 radians */
  val deg135: Angle = Angle(3 * Pi / 4)
  /** 150 degrees anti-clockwise or + 5 * Pi/6 radians */
  val deg150: Angle = Angle(5 * Pi / 6)
  /** 180 degrees or Pi radians */
  def deg180: Angle = Angle(Pi)   
}
