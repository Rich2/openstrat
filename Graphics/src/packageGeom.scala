/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import math._, Colour.Black

/** The package name has been chosen to avoid clashing with "geometry" that may be use in other libraries This package contains Basic geometry. A
 *  number of implementation Value classes of the Int and Double product classes defined in ostrat. 2d graphical objects for generalised use. They are
 *  of particular use for the generic canvas based classes defined in pCanv but can be used in any display framework and for printing. */
package object geom
{
  val Phi: Double = 1.6180339887498948482
  implicit def affineToExtensions[T](value: T)(implicit ev: AffineTrans[T]): AffineExtensions[T] = new AffineExtensions[T](value, ev)
  implicit def transSimToExtension[T](value: T)(implicit ev: TransSim[T]): TransSimExtension[T] = new TransSimExtension[T](value, ev)
  
  implicit def slateToExtensions[T](value: T)(implicit ev: Slate[T]): SlateExtensions[T] = new SlateExtensions[T](value, ev)
  //implicit def rotateAxesToExtensions[T](value: T)(implicit ev: RotateAxes[T]): RotateAxesExtensions[T] = new RotateAxesExtensions[T](value, ev)
  implicit def rotateToExtensions[T](value: T)(implicit ev: Rotate[T]): RotateExtensions[T] = new RotateExtensions[T](value, ev)  
  implicit def scaleToExtensions[T](value: T)(implicit ev: Scale[T]): ScaleExtensions[T] = new ScaleExtensions[T](value, ev)
  implicit def xyScaleToExtensions[T](value: T)(implicit ev: XYScale[T]): XYScaleExtensions[T] = new XYScaleExtensions[T](value, ev)
  implicit def shearToExtensions[T](value: T)(implicit ev: Shear[T]): ShearExtensions[T] = new ShearExtensions[T](value, ev)
  implicit class ProlignMatrixExtension[T](val value: T)(implicit ev: Prolign[T])
  { def prolign(matrix: ProlignMatrix): T = ev.prolignObj(value, matrix)
  }
 
  implicit def mirrorAxisOffsetToExtension[T](value: T)(implicit ev: ReflectAxisOffset[T]): ReflectAxisOffsetExtension[T] =
    new ReflectAxisOffsetExtension[T](value)(ev)

  implicit def mirrorAxisToExtension[T](value: T)(implicit ev: ReflectAxis[T]): ReflectAxisExtension[T] = new ReflectAxisExtension[T](value)(ev)
    
  /** The ratio of the degoid to a degree. Th degoid has been chosen as a convenient way to encode Angles using an underlying Double floating point
   * value. It has been chosen to allow the precise encoding of degrees and decimals of a degree down to 1 millionth. */  
  val secsInDeg: Int = 3600

  val secsIn360Degs: Int = secsInDeg * 360
  val secsIn180Degs: Int = secsInDeg * 180
  val secsIn90Degs: Int = secsInDeg * 90
  val secsInRadian: Double = secsInDeg * 180.0 / Pi

  /** seconds value for 90 degrees of Pi/2 radians. */
  val deg90InSecs = secsInDeg * 90

  /** seconds value for 180 degrees or Pi radians. */
  val deg180InSecs = secsInDeg * 180

  val degM90InSecs: Int = - secsInDeg * 90
  val degM180InSecs: Int = - secsInDeg * 180
  
  /** The origin, the intersection of the axes for 2 dimensional vectors. */
  val Vec2Z = Vec2(0, 0)  
  
  /** Dist2(0.km, 0.km) constant */
  val Dist2Z = Dist2(0.km, 0.km)
  
  val LongD = 2.0 / Cos30
  val cos30: Double = cos(Pi / 6)
  val LatLong0 = LatLong.degs(0, 0)
  val EarthPolarRadius: Dist = 6356.7523.km
  val EarthEquatorialRadius: Dist = 6378.137.km
  val EarthAvDiameter: Dist = 12742.km
  val EarthAvRadius: Dist = EarthAvDiameter / 2
  type DisplayElems = Arr[GraphicElem]

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
  {  def canvObjsPair(f: A => (Seq[GraphicAffineElem], Seq[GraphicAffineElem])): (Seq[GraphicAffineElem], Seq[GraphicAffineElem]) = thisOption match
     {
        case Some(a) => f(a)
        case None => (Seq(), Seq())
     }
  }
   
  /** 0 degrees or 0 radians */
  def deg0: Angle = Angle(0)
  /** 15 degrees anti-clockwise or + Pi/12 radians */
  val deg15: Angle = Angle(15)
  /** 30 degrees anti-clockwise or + Pi/6 radians */
  val deg30: Angle = Angle(30)
  /** 36 degrees anti-clockwise or + Pi/5 radians */
  val deg36: Angle = Angle(36)
  /** 45 degrees anti-clockwise or + Pi/4 radians */
  val deg45: Angle = Angle(45)
  /** 60 degrees anti-clockwise or + Pi/3 radians */
  val deg60: Angle  = Angle(60)  
  /** 72 degrees anti-clockwise or + Pi2/5 radians */
  val deg72: Angle = Angle(72)
  /** 90 degrees anti-clockwise or + Pi/2 radians */
  val deg90: Angle = Angle(90)  
  /** 120 degrees anti-clockwise or + 2 * Pi/3 radians */
  val deg120: Angle = Angle(120)
  /** 135 degrees anti-clockwise or + 3 * Pi/4 radians */
  val deg135: Angle = Angle(135)
  /** 150 degrees anti-clockwise or + 5 * Pi/6 radians */
  val deg150: Angle = Angle(150)
  /** 180 degrees or Pi radians */
  def deg180: Angle = Angle(180)

  def displayRowGraphics(leftPt: Vec2, actives: Arr[GraphicBounded], margin: Double = 10): Arr[GraphicBounded] =
    actives.mapWithAcc(leftPt.x + margin)((head, x) => (head.xSlate(x + head.boundingWidth / 2), x + head.boundingWidth + margin))
}