/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** The package name has been chosen to avoid clashing with "geometry" that may be use in other libraries This package contains geometry vectors and
 * graphics. Of particular note are the [[Pt2]], [[GeomElem]] and [[GraphicElem]] class /traits. These are the foundation of the API and the library.
 * A number of implementation Value classes of the Int and Double product classes defined in ostrat. 2d graphical objects for generalised use. They
 * are of particular use for the generic canvas based classes defined in pCanv but can be used in any display framework and for printing. */
package object geom
{ import math.Pi, Colour.Black
  val Phi: Double = 1.6180339887498948482

  /** A [[Vec2]] along the X Axis, whose Y component = 0. */
  def xVec2(x: Double): Vec2 = Vec2(x, 0)

  /** A [[Vec2]] along the Y Axis, whose X component = 0. */
  def yVec2(y: Double): Vec2 = Vec2(0, y)

  /** Constructs a new [[ArrayBuffer]] of [[GraphicElem]]. */
  @inline def BuffGraphic(initialLength: Int = 5): ArrayBuffer[GraphicElem] = new ArrayBuffer[GraphicElem](initialLength)

  implicit def sequExtension[A](value: Sequ[A]): SequExtensions[A] = new SequExtensions[A](value)
  implicit def seqDefExtension[A](value: SeqSpec[A]): SeqSpecExtensions[A] = new SeqSpecExtensions[A](value)

  implicit def transSimToExtension[T](value: T)(implicit ev: TransSim[T]): TransSimExtension[T] = new TransSimExtension[T](value, ev)
  implicit def slateToExtensions[T](value: T)(implicit ev: Slate[T]): SlateExtensions[T] = new SlateExtensions[T](value, ev)
  implicit def boundedToExtensions[T <: BoundedElem](value: T): BoundedExtensions[T] = new BoundedExtensions[T](value)
  implicit def scaleToExtensions[T](value: T)(implicit ev: Scale[T]): ScaleExtensions[T] = new ScaleExtensions[T](value, ev)
  implicit def transAxesToExtensions[T](value: T)(implicit ev: TransAxes[T]): TransAxesExtensions[T] = new TransAxesExtensions[T](value)(ev)

  implicit def transAxesSlateToExtensions[T](value: T)(implicit evR: TransAxes[T], evS: Slate[T]): TransAxesSlateExtensions[T] =
    new TransAxesSlateExtensions[T](value)(evR, evS)

  implicit def rotateToExtensions[T, T1 <: T](value: T1)(implicit ev: Rotate[T]): RotateExtensions[T] = new RotateExtensions[T](value, ev)
  implicit def rotateM3ToExtensions[T, T1 <: T](value: T1)(implicit ev: RotateM3T[T]): RotateM3Extensions[T] = new RotateM3Extensions[T](value, ev)
  implicit def reflectToExtension[T](value: T)(implicit ev: Reflect[T]): ReflectExtensions[T] = new ReflectExtensions[T](value, ev)

  implicit def xyScaleToExtensions[T](value: T)(implicit ev: ScaleXY[T]): XYScaleExtensions[T] = new XYScaleExtensions[T](value, ev)
  implicit def shearToExtensions[T](value: T)(implicit ev: Shear[T]): ShearExtensions[T] = new ShearExtensions[T](value, ev)

  implicit def slateTransAxesToExtension[T](value: T)(implicit evS: Slate[T], evR: TransAxes[T]): SlateTransAxesExtensions[T] =
    new SlateTransAxesExtensions[T](value)(evS, evR)

  implicit def OrdinaledElemToExtensions[T <: OrdinaledElem](value: T)(implicit ev: Slate[T]): OrdinaledExtensions[T] =
    new OrdinaledExtensions[T](value)(ev)

  implicit class ProlignMatrixExtension[T](val value: T)(implicit ev: Prolign[T])
  { def prolign(matrix: ProlignMatrix): T = ev.prolignObj(value, matrix)
  }  
    
  /** The number of arc seconds in a degree = 3600. */
  val SecsInDeg: Int = 3600

  /** The number of thousandths of an arc second in a degree = 3600000. */
  val MilliSecsInDeg: Int = 3600000

  /** The number of arc seconds in 360 degrees = 3600000. */
  val SecsIn360Degs: Int = SecsInDeg * 360

  /** The number arc seconds in 180 degrees = 1800000. */
  val SecsIn180Degs: Int = SecsInDeg * 180

  /** The number arc seconds in 90 degrees = 900000. */
  val SecsIn90Degs: Int = SecsInDeg * 90

  /** The number arc seconds in a radian = 206264.80624709636. */
  val SecsInRadian: Double = SecsInDeg * 180.0 / Pi

  /** The number of thousands of an arc second in 360 degrees = 3600000000. */
  val MilliSecsIn360Degs: Int = MilliSecsInDeg * 360

  /** The number of thousands of an arc second in 180 degrees = 1800000000. */
  val MilliSecsIn180Degs: Int = MilliSecsInDeg * 180

  /** The number of thousands of an arc second in 90 degrees = 900000000. */
  val MilliSecsIn90Degs: Int = MilliSecsInDeg * 90

  /** The number of thousands of an arc second in a radian = 2.0626480624709636E8. */
  val MilliSecsInRadian: Double = MilliSecsInDeg * 180.0 / Pi
  
  /** The origin, the intersection of the axes for 2 dimensional vectors. */
  val Pt2Z = Pt2(0, 0)

  /** Dist2(0.km, 0.km) constant */
  val Dist2Z = PtM2(0.metres, 0.metres)

  val Sin15: Double = math.sin(Pi/12)
  val Sin30: Double = 0.5
  val Sin60: Double = math.sin(Pi/3)
  val Cos30: Double = math.cos(Pi/6)
  val Cos60: Double = 0.5

  /** The Earths polar radius 6356.7523 km. */
  val EarthPolarRadius: Length = 6356752.3.metres

  /** The Earths equatorial radius 6378.137 km. */
  val EarthEquatorialRadius: Length = 6378137.metres


  val EarthAvDiameter: Length = 12742000.metres
  val EarthAvRadius: Length = EarthAvDiameter / 2
  type GraphicElems = RArr[GraphicElem]

  implicit def intToImplicitGeom(thisInt: Int): IntGeomImplicit = new IntGeomImplicit(thisInt)
  implicit def doubleToImplicitGeom(thisDouble: Double): DoubleImplicitGeom = new DoubleImplicitGeom(thisDouble)

  implicit class AnyGeomImplicit(thisAny: Any)
  {
    /** Creates a [[TextGraphic]] at the given [[Pt2], default x = 0, y = 0, using the toString method on this object. */
    def toTextGraphic(fontSize: Double = 24, posn: Pt2 = Pt2Z, colour: Colour = Black, align: TextAlign = CenAlign,
     baseLine: BaseLine = BaseLine.Middle): TextGraphic = TextGraphic(thisAny.toString, fontSize, posn, colour, align, baseLine)

    /** Creates a [[TextGraphic]] at the given X and Y positions, using the toString method on this object. */
    def xyTextGraphic(fontSize: Double = 24, xPosn: Double, yPosn: Double, colour: Colour = Black, align: TextAlign = CenAlign,
      baseLine: BaseLine = BaseLine.Middle): TextGraphic = TextGraphic.xy(thisAny.toString, fontSize, xPosn, yPosn, colour, align, baseLine)
  }

  implicit class StringImplictGeom(thisString: String)
  {
    import pParse.{ stringToStatements => stss}

    /** Find unique [[Pt2]] expression from this String parsing it as an Sequence of RSON statements. */
    def findPt2: EMon[Pt2] = stss(thisString).flatMap(_.findUniqueT[Pt2])

    /** Find unique [[Pt2]] expression from this String, or return default [[Pt2]] value, parsing it as an Sequence of RSON statements. */
    def findPt2Else(elseValue: => Pt2) = findPt2.getElse(elseValue)

    /** Find unique [[Pt2]] setting of the given name from this String, parsing it as an Sequence of RSON statements. */
    def findSettingPt2(setting: String): EMon[Pt2] = stss(thisString).flatMap(_.findSetting[Pt2](setting))

    /** Find unique [[Pt2]] setting of the given name from this String, or return default [[Pt2]] value, parsing it as an Sequence of RSON
     *  statements. */
    def findSettingPt2Else(setting: String, elseValue: Pt2): Pt2 = findSettingPt2(setting).getElse(elseValue)

    def graphic(fontSize: Int = 24, posn: Pt2 = Pt2Z, colour: Colour = Black, align: TextAlign = CenAlign,
                baseLine: BaseLine = BaseLine.Alphabetic): TextGraphic = TextGraphic(thisString, fontSize, posn, colour, align, baseLine)
  }

  implicit class BuffDblExtensionsImplicit[A <: DblNElem](val thisBuff: DblNBuff[A])
  { /** Extension method to create Polygons from [[DblNBuff]]. Takes an implicit [[PolygonDblNMapBuilder]] parameter to return the [[PolygonLikeDblN]] */
    def toPolygon[PT <: PolygonLikeDblN[A]](implicit build: PolygonDblNMapBuilder[A, PT]): PT = build.fromDblArray(thisBuff.unsafeBuffer.toArray)
  }

  implicit class BuffIntExtensionsImplicit[A <: IntNElem](val thisBuff: IntNBuff[A])
  { /** Extension method to create Polygons from [[IntNBuff]]. Takes an implicit [[PolygonIntNMapBuilder]] parameter to return the [[PolygonLikeIntN]] */
    def toPolygon[PT <: PolygonLikeIntN[A]](implicit build: PolygonIntNMapBuilder[A, PT]): PT = build.fromIntArray(thisBuff.unsafeBuffer.toArray)
  }

  implicit class MetreExtensionsImplicit(thisMetres: Length)
  {  def / (operand: Length): Double = thisMetres.metresNum / operand.metresNum
  }

  implicit class IterableExtensions[A](val thisIter: Iterable[A]) extends AnyVal
  {
    /** Converts to a [[LinePathLike]] with points of type A. */
    def toLinePath[AA <: LinePathLike[A]](implicit builder: LinePathBuilder[A, AA]): AA =
    {
      val len = thisIter.size
      val res = builder.uninitialised(len)
      thisIter.iForeach((i, a) => res.setElemUnsafe(i, a))
      res
    }

    /** Converts to a [[PolygonLike]] with points of type A. */
    def toPolygon[AA <: PolygonLike[A]](implicit builder: PolygonLikeMapBuilder[A, AA]): AA =
    { val len = thisIter.size
      val res = builder.uninitialised(len)
      thisIter.iForeach((i, a) => res.setElemUnsafe(i, a))
      res
    }
  }
   
  /** 0 degrees or 0 radians */
  val Deg0: AngleVec = AngleVec(0)

  /** An absolute angle of 0 degrees or 0 radians. */
  val Ang0: Angle = Angle(0)

  /** An absolute angle of 30 degrees or Pi/6 radians. */
  val Ang30: Angle = Angle(30)

  /** An absolute angle of 45 degrees or Pi/4 radians. */
  val Ang45: Angle = Angle(45)

  /** An absolute angle of 60 degrees or Pi/3 radians. */
  val Ang60: Angle = Angle(60)

  /** 90 degrees anti-clockwise or + Pi/2 radians */
  val Ang90: Angle = Angle(90)

  /** An absolute angle of 120 degrees or 2 Pi/3 radians. */
  val Ang120: Angle = Angle(120)

  /** An absolute angle of 150 degrees or 5 Pi/6 radians. */
  val Ang150: Angle = Angle(150)

  /** An absolute angle of 240 degrees or 4 Pi/3 radians. */
  val Ang240: Angle = Angle(240)

  /** An absolute angle of 300 degrees or 5 Pi/3 radians. */
  val Ang300: Angle = Angle(300)

  /** An absolute angle of 315 degrees or 7 Pi/4 radians. */
  val Ang315: Angle = Angle(315)

  /** An absolute angle of 330 degrees or 11 Pi/6 radians. */
  val Ang330: Angle = Angle(330)

  /** 15 degrees anti-clockwise or + Pi/12 radians */
  val DegVec15: AngleVec = AngleVec(15)

  /** 25 degrees anti-clockwise or +25 degrees. */
  val DegVec25: AngleVec = AngleVec(25)

  /** 30 degrees anti-clockwise or + Pi/6 radians */
  val DegVec30: AngleVec = AngleVec(30)

  /** 36 degrees anti-clockwise or + Pi/5 radians */
  val DegVec36: AngleVec = AngleVec(36)

  /** 45 degrees anti-clockwise or + Pi/4 radians */
  val DegVec45: AngleVec = AngleVec(45)

  /** 60 degrees anti-clockwise or + Pi/3 radians */
  val DegVec60: AngleVec  = AngleVec(60)

  /** 72 degrees anti-clockwise or + Pi2/5 radians */
  val DegVec72: AngleVec = AngleVec(72)

  /** 90 degrees anti-clockwise or + Pi/2 radians */
  val DegVec90: AngleVec = AngleVec(90)

  /** 120 degrees anti-clockwise or + 2 * Pi/3 radians */
  val DegVec120: AngleVec = AngleVec(120)

  /** 135 degrees anti-clockwise or + 3 * Pi/4 radians */
  val DegVec135: AngleVec = AngleVec(135)

  /** 150 degrees anti-clockwise or + 5 * Pi/6 radians */
  val DegVec150: AngleVec = AngleVec(150)

  /** 180 degrees or Pi radians */
  val DegVec180: AngleVec = AngleVec(180)

  /** 210 degrees or Pi radians */
  val DegVec210: AngleVec = AngleVec(210)

  /** 240 degrees or Pi radians */
  val DegVec240: AngleVec = AngleVec(240)

  /** 270 degrees or Pi radians */
  val DegVec270: AngleVec = AngleVec(270)

  /** 300 degrees or Pi radians */
  val DegVec300: AngleVec = AngleVec(300)

  def displayRowGraphics(leftPt: Pt2, actives: RArr[GraphicBounded], margin: Double = 10): RArr[GraphicBounded] =
    actives.mapWithAcc(leftPt.x + margin)((x, head) => (head.slateX(x + head.boundingWidth / 2), x + head.boundingWidth + margin))
    
  def displayRowTexts(leftPt: Pt2, textCells: RArr[TextCell], fontSize: Double = 18, margin: Double = 10): RArr[BoundedElem] =
  {
    val y = leftPt.y
    var acc: ArrayBuffer[BoundedElem] = Buffer()
    var xAcc: Double = 0
    textCells.foreach{tc =>
      val rLen = tc.textStr.length * fontSize
      xAcc = rLen / 2
      val rect: Rectangle = Rect(rLen, fontSize, xAcc pp y)
      val newElem = rect.fill(Colour.Green)
      acc = acc :+ newElem
      xAcc += rLen / 2
    }
    //textCells.mapWithAcc(leftPt.x + margin)((head, x) => (Vec2(x + head.textStr.length / 2, leftPt.y), x + head.textStr.length + margin))
    acc.toArr
  }

  /** Maps a range of Ints to [[PolygonLike]][A]. From 0 until the iUntil parameter value in steps of 1. Throws on non termination. Method
   * name over loaded with a range of integers from parameter 1 until parameter 2 in steps of parameter 3. */
  def iUntilPolygonLikeMap[A, AA <: PolygonLike[A]](iUntil: Int)(f: Int => A)(implicit ev: PolygonLikeMapBuilder[A, AA]): AA = {
    val iLen = (iUntil).max(0)
    val res: AA = ev.uninitialised(iLen)
    var index = 0
    iUntilForeach(iUntil) { count => ev.indexSet(res, index, f(count)); index += 1 }
    res
  }

  /** Maps a range of Ints to a [[PolygonLike]][A]. From the iFrom value until the iUntil value in steps of iStep. Default step value is 1. Throws
   * on non termination. Method name over loaded with a first parameter list of a single iUntil parameter, where iFrom is 0 and iStep is 1. */
  def iUntilpolygonLikeMap[A, AA <: PolygonLike[A]](iFrom: Int, iUntil: Int, iStep: Int = 1)(f: Int => A)(implicit ev: PolygonLikeMapBuilder[A, AA]): AA =
  { val iLen = (iUntil - iFrom).max(0) / iStep
    val res: AA = ev.uninitialised(iLen)
    var index = 0
    iUntilForeach(iFrom, iUntil, iStep) { count => ev.indexSet(res, index, f(count)); index += 1 }
    res
  }

  /** Maps over a range of Ints to a [[PolygonLike]][A]. From the iFrom parameter value to the iTo parameter value in integer steps. Default step
   *  value is 1.Throws on non termination. Method name over loaded with a first parameter list of a single iUntil parameter, where iFrom is 0 and
   * iStep is 1. */
  def iToPolygonLikeMap[A, AA <: PolygonLike[A]](iFrom: Int, iTo: Int, iStep: Int = 1)(f: Int => A)(implicit ev: PolygonLikeMapBuilder[A, AA]): AA =
  { val iLen = (iTo - iFrom + iStep).max(0) / iStep
    val res: AA = ev.uninitialised(iLen)
    var index = 0
    iToForeach(iFrom, iTo, iStep) { count => ev.indexSet(res, index, f(count)); index += 1 }
    res
  }

  /** Maps over a range of Ints to a [[PolygonLike]][A]. From 0 to to the iTo value in steps of 1. Throws on non termination. Method name over loaded
   *  with a range of integers from parameter 1 to parameter 2 in steps of parameter 3. */
  def iToPolygonLikeMap[A, AA <: PolygonLike[A]](iTo: Int)(f: Int => A)(implicit ev: PolygonLikeMapBuilder[A, AA]): AA = {
    val iLen = (iTo + 1).max(0)
    val res: AA = ev.uninitialised(iLen)
    var index = 0
    iToForeach(iTo) { count => ev.indexSet(res, index, f(count)); index += 1 }
    res
  }
}