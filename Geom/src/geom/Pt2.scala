/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math._, collection.mutable.ArrayBuffer, Colour.Black

/** A 2 dimensional point. Pt2s can be transformed through the 2D geometric transformations. If you wish to encode a relative position then use a
 *  [[Vec2]] instead. Thanks to René Descartes for this. [[Vec2]]s can be added and subtracted from points. Points can not be added to points but they
 *  can be used to translate the point. */
final class Pt2(val x: Double, val y: Double) extends Vec2Like
{ override def typeStr: String = "Pt2"
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Pt2]

  override def approx(that: Any, precision: Double): Boolean = that match
  { case other: Pt2 => dblsApprox(other, precision)
    case _ => false
  }

  override def equals(that: Any): Boolean = that match
  { case that: Pt2 => dblsEqual(that)
    case _ => false
  }

  /** Add the operand [[Vec2]] 2D vector to this Pt2, returns a new Pt2. */
  def +(operand: Vec2): Pt2 = Pt2(x + operand.x, y + operand.y)

  /** Subtracts the operand [[Vec2]] 2D vector from this 2D point. Returns a new [[Pt2]]. */
  def - (operand: Vec2): Pt2 = Pt2(x - operand.x, y - operand.y)

  /** Returns the [[Vec2]] 2D vector from the origin to this Pt2. */
  def toVec: Vec2 = Vec2(x, y)

  /** Subtracts the operand 2D point from this 2D point to get the relative Vector. */
  def <<(startPt: Pt2): Vec2 = Vec2(x - startPt.x, y - startPt.y)

  /** Subtracts this 2D point from the operand 2D point to get the relative Vector. */
  def >>(operand: Pt2): Vec2 = Vec2(operand.x - x, operand.y - y)

  /** Subtracts this 2D point from the operand 2D point and halves it to get the relative Vector divided by 2. This is a very common operation when
   * calculating the distance along an axis and the distance to the centre point is required. Hence the specific method */
  def >/>(operand: Pt2): Vec2 = Vec2(operand.x - x, operand.y - y)

  /** Gives the positive scalar distance between this and the operand Vec2. */
  def distTo(operand: Pt2): Double = (this >> operand).magnitude

  /** Gives the angle from this point to the operand point. */
  def angleTo(operand: Pt2): Angle = (this >> operand).angle

  /** Gives the anlge from the operand point to this point. */
  def angleFrom(operand: Pt2): Angle = (this << operand).angle

  /** Gives the anlge from the operand point to this point. */
  def angleFromYDown(operand: Pt2): Angle = (this << operand).angleYDown

  /** The average of this and the operand Pt2. The mid point between this point and the operand second point. */
  def midPt(point2: Pt2): Pt2 = Pt2(x + point2.x, y + point2.y).invScale(2)

  def strMod(f: Double => String): String = "Pt2".appendParenthSemis(f(x), f(y))

  /** 2D geometric translation transofrmation on this Pt2 returns a Pt2. */
  def xySlate(xOperand: Double, yOperand: Double): Pt2 = Pt2(x + xOperand, y + yOperand)

  /** 2D geometric translation transformation on this Pt2 returns a Pt2. */
  def slate(operand: Vec2Like): Pt2 = Pt2(x + operand.x, y + operand.y)

  /** Changes the origin of the point to the new point. Subtracting the X and Y components of the operand point from this point. */
  def invSlate(operand: Pt2): Pt2 = Pt2(x - operand.x, y - operand.y)

  def addXY (otherX: Double, otherY: Double): Pt2 = Pt2(x + otherX, y + otherY)
  def subXY (otherX: Double, otherY: Double): Pt2 = Pt2(x - otherX, y - otherY)

  @inline def scale(factor: Double): Pt2 = Pt2(x * factor, y * factor)
  @inline def toMetres(factor: Length): PtM2 = PtM2(x * factor, y * factor)

  @inline def invScale(divisor: Double): Pt2 = Pt2(x / divisor, y / divisor)

  def addX(adj: Double): Pt2 = Pt2(x + adj, y)
  def addY(adj: Double): Pt2 = Pt2(x, y + adj)
  def subX(adj: Double): Pt2 = Pt2(x - adj, y)
  def subY(adj: Double): Pt2 = Pt2(x, y - adj)

  def yScale(factor: Double): Pt2 = Pt2(x, y * factor)
  def xScale(factor: Double): Pt2 = Pt2(x * factor, y)
  def xyScale(xOperand: Double, yOperand: Double): Pt2 = Pt2(x * xOperand, y * yOperand)

  /** Reflects or mirrors this Vec2 across a line, returning new Vec2. */
  def reflect(lineLike: LineLike): Pt2 = lineLike match
  {
    case xl: XLine => reflectXLine(xl)
    case yl: YLine => reflectYLine(yl)
    case r: Ray => ???

    case lineSeg: LineSeg =>
    { val v1 = lineSeg.pStart
      val v2 = lineSeg.pEnd
      val lineDelta = v2 << v1
      val lineUnitVector = lineDelta / lineDelta.magnitude
      val r1 = v1 << this
      val r2 = r1 - 2 * v1.<<(this).dot(lineUnitVector) * lineUnitVector
      v1 + r2
    }
  }

  /** Reflects, mirrors this Vec2 across an XLine. */
  def reflectXLine(line: XLine): Pt2 = Pt2(x, -y)

  /** Reflects, mirrors this Vec2 across a YLine. */
  def reflectYLine(line: YLine): Pt2 = ???

  /** Reflects ,mirrors along the X axis by negating Y. */
  def negY: Pt2 = Pt2(x, -y)

  /** Reflects, mirrors along the Y axis by negating X. */
  def negX: Pt2 = Pt2(-x, y)

  /** Reflects, mirrors along the y = yOffset line that is parallel to the  X axis. */
  def reflectXParallel(yOffset: Double): Pt2 = Pt2(x, -y + yOffset * 2)

  /** Reflects, mirrors along the x = xOffset line, that is parallel to the Y axis by negating X. */
  def reflectYParallel(xOffset: Double): Pt2 = Pt2(-x + xOffset * 2, y)

  def xShear(operand: Double): Pt2 = Pt2(x, y * operand)
  def yShear(operand: Double): Pt2 = Pt2(x * operand, y)

  @inline def prolign(m: ProlignMatrix): Pt2 = m.vecTrans(this)

  /** Reverses the y coordinate. Useful for translating between canvases where the y axis measures down and coordinate systems where y is up */
  def inverseY: Pt2 = Pt2(x, -y)

  def toTuple: Tuple2[Double, Double] = (x, y)

  def pp(z: Double): Pt3 = Pt3(x, y, z)

  /** rotates the vector 90 degrees or Pi/2 radians, anticlockwise. */
  @inline def rotate90: Pt2 = Pt2(-y, x)

  /** Rotates the vector 180 degrees or Pi radians. */
  @inline def rotate180: Pt2 = Pt2(-x, -y)

  /** rotates the vector 90 degrees or Pi/2 radians, clockwise. */
  @inline def rotate270: Pt2 = Pt2(y, -x)

  /** Line segment [[LineSeg]] from this point to the parameter point. */
  def lineTo(pt2: Pt2): LineSeg = LineSeg(this, pt2)

  /** Line segment [[LineSeg]] from the parameter point to this point. */
  def lineFrom(pt2: Pt2): LineSeg = LineSeg(pt2, this)

  /** Line segment from this point to along the given angle for the given magnitude to point 2. */
  def angleToLine(angle: Angle, magnitude: Double): LineSeg = LineSeg(this, this + angle.toVec2(magnitude))

  /** Line segment from this point to along the given angle for the given magnitude to point 2. */
  def angleFromLine(angle: Angle, magnitude: Double): LineSeg = LineSeg(this + angle.toVec2(magnitude), this)

  /** Rotates this vector through the given angle around the origin. */
  def rotate(a: AngleVec): Pt2 = a match
  { case Deg0 => this
    case DegVec90 => rotate90
    case DegVec180 => rotate180
    case DegVec270 => rotate270
    case a => Pt2(x * a.cos - y * a.sin, x * a.sin + y * a.cos)
  }

  def rotateRadians(r: Double): Pt2 = Pt2(x * cos(r) - y * sin(r),
    { val ya = x * sin(r)
      val yb =y * cos(r)
      ya + yb
    })

  /** Rotates this vector through the given angle around the centre of rotation passed as the first parameter. */
  def rotateAbout(centre: Pt2, a: AngleVec): Pt2 =
  {
    val rel: Vec2 = this << centre
    val rel2: Vec2 = a match {
      case Deg0 => rel
      case DegVec90 => rel.rotate90
      case DegVec180 => rel.rotate180
      case DegVec270 => rel.rotate270
      case a => Vec2(rel.x * a.cos - rel.y * a.sin, rel.x * a.sin + rel.y * a.cos)
    }
    centre + rel2
  }

  def centreSquare(length: Double): PolygonGen =
  { val r = length / 2.0
    PolygonGen(-r pp r, r pp r, r pp -r, -r pp -r).slateXY(x, y)
  }

  def textAt(str: String, fontSize: Int, fontColour: Colour = Colour.Black): TextGraphic = TextGraphic(str, fontSize, this, fontColour)
  def toText(fontSize: Int = 10, fontColour: Colour = Colour.Black): TextGraphic = TextGraphic(str1, fontSize, this, fontColour)

  /*def arcControlPoint(pt2: Pt2, arcCentre: Pt2): Pt2 =
  { val angle1 = (this - arcCentre).angle
    val angle2 = (pt2 - arcCentre).angle
    val resultAngle =  angle1.bisect(angle2)
    val alphaAngle =  resultAngle / 2
    val radius = (pt2 - arcCentre).magnitude
    arcCentre + resultAngle.toVec2(radius / alphaAngle.cos)
  }*/

  def linesCross(armLength: Double = 5): Seq[LineSeg] = Seq( new LineSeg(x - armLength, y , x + armLength, y),
    new LineSeg(x, y - armLength, x, y + armLength))

  /** Not sure about this method */
  def drawCross(armLength: Double, lineColour: Colour, lineWidth: Double): LinesDraw =
    LineSegArr.fromDbls(x - armLength, y, x + armLength, y,
    x, y - armLength, x, y + armLength).draw(lineWidth, lineColour)

  //def alignMatrix(matrix: AlignMatrix): Pt2 = Pt2(x * matrix.xFactor, y * matrix.yFactor) + matrix.vDelta

  /** Creates a [[TextGraphic]] and a line segment with an arrow head at the end. The arrow pointing from the [[TextGraphic]] to this point. The
   * alignment of the text is determined by the angle parameter. The method is not meant to cover all possible configurations for text arrows. These
   * can easily be constructed from OpenStrat primitives, but to provide a quick default for rapid development. */
  def textArrow(str: String, angle: Angle = Ang45, arrowLength: Double = 20, colour: Colour = Black, fontSize: Double = 14): GraphicElems =
  {
    val align: TextAlign = angle match
    { case a if a <= Ang60 => LeftAlign
      case a if a > Ang60 & a < Ang120 => CenAlign
      case a if a >= Ang120 & a <= Ang240 => RightAlign
      case a if a > Ang240 & a < Ang300 => CenAlign
      case _ => LeftAlign
    }
    val tg = TextGraphic(str, fontSize, this.slateAngle(angle, arrowLength + 4), colour, align)
    this.angleFromLine(angle, arrowLength).withArrow(colour) +% tg
  }

  /** Creates a [[TextGraphic]] and a line segment with an arrow head at the end. The arrow pointing from the [[TextGraphic]] to this point. The arrow
   * points towards the dirnPt parameter point. The alignment of the text is determined by the angle parameter. */
  def textArrowToward(dirnPt: Pt2, str: String = toString, arrowLength: Double = 20, colour: Colour = Black, fontSize: Double = 14): GraphicElems =
    textArrow(str, angleFrom(dirnPt), arrowLength, colour, fontSize)

  /** Creates a [[TextGraphic]] and a line segment with an arrow head at the end. The arrow pointing away from the [[TextGraphic]] to this point. The arrow
   * points towards the dirnPt parameter point. The alignment of the text is determined by the angle parameter. */
  def textArrowAwayFrom(dirnPt: Pt2, str: String = toString, arrowLength: Double = 20, colour: Colour = Black, fontSize: Double = 14): GraphicElems =
    textArrow(str, angleTo(dirnPt), arrowLength, colour, fontSize)
}

/** Companion object for Pt2. Contains apply factory and unapply methods. Persist and EqT implicit type classes instances and instances for all the
 * 2D geometric transformation type classes. */
object Pt2
{ /** apply factory method for [[Pt2]]s. */
  def apply(x: Double, y: Double): Pt2 = new Pt2(x, y)

  /** unapply extractor method for [[Pt2]]s. */
  def unapply(orig: Pt2): Option[(Double, Double)] = Some((orig.x, orig.y))

  implicit class Pt2Implicit(thisPt: Pt2)
  { def * (operand: Length): PtM2 = PtM2(thisPt.x * operand, thisPt.y * operand)

  }

  def circlePt(angle: Double): Pt2 = Pt2(cos(angle), sin(angle))
  def circlePtClockwise(angle: Double): Pt2 = Pt2(cos(angle), - sin(angle))

  /** implicit [[Persist]] type class instance / evidence for [[Pt2]]s. */
  implicit val persistImplicit: PersistShowDbl2[Pt2] = new PersistShowDbl2[Pt2]("Pt2", "x", "y", apply)

  implicit val eqTImplicit: EqT[Pt2] = (pt1, pt2) => pt1.x == pt2.x & pt1.y == pt2.y
  implicit val approxTImplicit: ApproxT[Double, Pt2] = Approx2DblsT[Pt2](_.x, _.y)

  implicit val buildImplicit: Dbl2ArrBuilder[Pt2, Pt2s] = new Dbl2ArrBuilder[Pt2, Pt2s]
  { override type BuffT = BuffPt2
    override def fromDblArray(array: Array[Double]): Pt2s = new Pt2s(array)
    override def fromDblBuffer(buffer: ArrayBuffer[Double]): BuffPt2 = new BuffPt2(buffer)
  }

  implicit val linePathBuildImplicit: LinePathDbl2Builder[Pt2, LinePath] = new LinePathDbl2Builder[Pt2, LinePath]
  { override type BuffT = BuffPt2
    override def fromDblArray(array: Array[Double]): LinePath = new LinePath(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): BuffPt2 = new BuffPt2(inp)
  }

  implicit val polygonBuildImplicit: PolygonLikeBuilder[Pt2, Polygon] = new PolygonLikeBuilder[Pt2, PolygonGen]
  { override type BuffT = BuffPt2

    override def newPolygonT(length: Int): PolygonGen = PolygonGen.uninitialised(length)

    override def arrSet(arr: PolygonGen, index: Int, value: Pt2): Unit = arr.unsafeSetElem(index, value)

    /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
    override def buffGrow(buff: BuffPt2, value: Pt2): Unit = buff.grow(value)

    /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
    override def buffGrowArr(buff: BuffPt2, arr: PolygonGen): Unit = ???

    override def newBuff(length: Int): BuffPt2 = BuffPt2.empty

    override def buffToBB(buff: BuffPt2): PolygonGen = ???
  }

  implicit val lineSegBuildEv: LineSegBuilder[Pt2, LineSeg] = LineSeg(_, _)

  implicit val slateImplicit: Slate[Pt2] = (obj: Pt2, dx: Double, dy: Double) => obj.xySlate(dx, dy)
  implicit val scaleImplicit: Scale[Pt2] = (obj: Pt2, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Pt2] = (obj: Pt2, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[Pt2] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: ScaleXY[Pt2] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val reflectImplicit: Reflect[Pt2] = (obj: Pt2, lineLike: LineLike) => obj.reflect(lineLike)

  implicit val reflectAxesImplicit: TransAxes[Pt2] = new TransAxes[Pt2]
  { override def negYT(obj: Pt2): Pt2 = obj.negY
    override def negXT(obj: Pt2): Pt2 = obj.negX
    override def rotate90(obj: Pt2): Pt2 = obj.rotate90
    override def rotate180(obj: Pt2): Pt2 = obj.rotate180
    override def rotate270(obj: Pt2): Pt2 = obj.rotate270
  }

  implicit val shearImplicit: Shear[Pt2] = new Shear[Pt2]
  { override def shearXT(obj: Pt2, yFactor: Double): Pt2 = obj.xShear(yFactor)
    override def shearYT(obj: Pt2, xFactor: Double): Pt2 = obj.yShear(xFactor)
  }
}