/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import math._, Colour.Black

/** The package name has been chosen to avoid clashing with "geometry" that may be use in other libraries This package contains geometry vectors and
 * graphics. Of particular note are the [[Vec2]], [[GeomElem]] and [[GraphicElem]] class /traits. These are the foundation of the API and the library.
 * A number of implementation Value classes of the Int and Double product classes defined in ostrat. 2d graphical objects for generalised use. They
 * are of particular use for the generic canvas based classes defined in pCanv but can be used in any display framework and for printing. */
package object geom
{
  val Phi: Double = 1.6180339887498948482
  implicit def affineToExtensions[T](value: T)(implicit ev: AffineTrans[T]): AffineExtensions[T] = new AffineExtensions[T](value, ev)
  implicit def transSimToExtension[T](value: T)(implicit ev: TransSim[T]): TransSimExtension[T] = new TransSimExtension[T](value, ev)
  
  implicit def slateToExtensions[T](value: T)(implicit ev: Slate[T]): SlateExtensions[T] = new SlateExtensions[T](value, ev)
  implicit def scaleToExtensions[T](value: T)(implicit ev: Scale[T]): ScaleExtensions[T] = new ScaleExtensions[T](value, ev)
  implicit def transAxesToExtension[T](value: T)(implicit ev: TransAxes[T]): TransAxesExtension[T] = new TransAxesExtension[T](value)(ev)  
  implicit def rotateToExtensions[T, T1 <: T](value: T1)(implicit ev: Rotate[T]): RotateExtensions[T] = new RotateExtensions[T](value, ev)
  
  implicit def xyScaleToExtensions[T](value: T)(implicit ev: XYScale[T]): XYScaleExtensions[T] = new XYScaleExtensions[T](value, ev)
  implicit def shearToExtensions[T](value: T)(implicit ev: Shear[T]): ShearExtensions[T] = new ShearExtensions[T](value, ev)

  implicit def slateTransAxesToExtension[T](value: T)(implicit evS: Slate[T], evR: TransAxes[T]): SlateTransAxesExtensions[T] =
    new SlateTransAxesExtensions[T](value)(evS, evR)

  implicit def slateToToExtensions[T](value: T)(implicit ev: SlateTo[T]): SlateToExtensions[T] = new SlateToExtensions[T](value, ev)

  implicit def boundedAlignedToToExtensions[T <: BoundedAligned](value: T)(implicit ev: SlateTo[T]): BoundedAlignedExtensions[T] =
    new BoundedAlignedExtensions[T](value, ev)

  implicit class ProlignMatrixExtension[T](val value: T)(implicit ev: Prolign[T])
  { def prolign(matrix: ProlignMatrix): T = ev.prolignObj(value, matrix)
  }  
    
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
  type GraphicElems = Arr[GraphicElem]

  implicit def intToImplicitGeom(thisInt: Int): IntGeomImplicit = new IntGeomImplicit(thisInt)           
  implicit def doubleToImplicitGeom(thisDouble: Double): DoubleImplicitGeom = new DoubleImplicitGeom(thisDouble)
 
  implicit class StringImplictGeom(thisString: String)
  { import pParse.{ stringToStatements => stss}
    def findVec2: EMon[Vec2] = stss(thisString).flatMap(_.findType[Vec2])
    def findVec2Else(elseValue: => Vec2) = findVec2.getElse(elseValue)
    def findVec2Sett(setting: String): EMon[Vec2] = stss(thisString).flatMap(_.findSett[Vec2](setting))
    def findVec2SettElse(setting: String, elseValue: Vec2): Vec2 = findVec2Sett(setting).getElse(elseValue)

    def graphic(fontSize: Int = 24, posn: Vec2 = Vec2Z, colour: Colour = Black, align: TextAlign = CenAlign,
      baseLine: BaseLine = BaseLine.Alphabetic): TextGraphic = TextGraphic(thisString, posn, fontSize, colour, align, baseLine)
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
  val Deg0: Angle = Angle(0)
  /** 15 degrees anti-clockwise or + Pi/12 radians */
  val Deg15: Angle = Angle(15)
  /** 30 degrees anti-clockwise or + Pi/6 radians */
  val Deg30: Angle = Angle(30)
  /** 36 degrees anti-clockwise or + Pi/5 radians */
  val Deg36: Angle = Angle(36)
  /** 45 degrees anti-clockwise or + Pi/4 radians */
  val Deg45: Angle = Angle(45)
  /** 60 degrees anti-clockwise or + Pi/3 radians */
  val Deg60: Angle  = Angle(60)
  /** 72 degrees anti-clockwise or + Pi2/5 radians */
  val Deg72: Angle = Angle(72)
  /** 90 degrees anti-clockwise or + Pi/2 radians */
  val Deg90: Angle = Angle(90)  
  /** 120 degrees anti-clockwise or + 2 * Pi/3 radians */
  val Deg120: Angle = Angle(120)
  /** 135 degrees anti-clockwise or + 3 * Pi/4 radians */
  val Deg135: Angle = Angle(135)
  /** 150 degrees anti-clockwise or + 5 * Pi/6 radians */
  val Deg150: Angle = Angle(150)
  /** 180 degrees or Pi radians */
  val Deg180: Angle = Angle(180)
  /** 240 degrees or Pi radians */
  val Deg240: Angle = Angle(240)
  /** 270 degrees or Pi radians */
  val Deg270: Angle = Angle(270)
  /** 300 degrees or Pi radians */
  val Deg300: Angle = Angle(300)

  def displayRowGraphics(leftPt: Vec2, actives: Arr[BoundedGraphic], margin: Double = 10): Arr[BoundedGraphic] =
    actives.mapWithAcc(leftPt.x + margin)((head, x) => (head.xSlate(x + head.boundingWidth / 2), x + head.boundingWidth + margin))
    
  def displayRowTexts(leftPt: Vec2, textCells: Arr[TextCell], fontSize: Double = 18, margin: Double = 10): Arr[BoundedElem] =
  {
    val y = leftPt.y
    var acc: Buff[BoundedElem] = Buff()
    var xAcc: Double = 0
    textCells.foreach{tc =>
      val rLen = tc.textStr.length * fontSize
      xAcc = rLen / 2
      val rect: Rectangle = Rect(rLen, fontSize, xAcc vv y)
      val newElem = rect.fill(Colour.Green)
      acc = acc :+ newElem
      xAcc += rLen / 2
    }
    //textCells.mapWithAcc(leftPt.x + margin)((head, x) => (Vec2(x + head.textStr.length / 2, leftPt.y), x + head.textStr.length + margin))
    acc.toArr
  }
}