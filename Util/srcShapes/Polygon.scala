/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._, Colour.Black

/** A mathematical closed polygon. The general case can be instantiated with [[PolygonGen]], but it provides the interface for particular sub sets of
 *  polygons such as triangles and square. Mathematically a closed polygon made up of straight line segments. The default convention is to number the
 *  vertices in a clockwise direction, with vertex 1 the first vertex that is clockwise from 12 O'Clock. Sides are numbered in a corresponding manner
 *  with then end point of side n sdn at vertex n. */
trait Polygon extends Shape with BoundedElem with Approx[Double] with Pt2SeqSpec with PolygonLikeDbl2[Pt2] with Dbl2SeqSpec[Pt2]
{
  override type SideT = LineSeg

  final def vLastX: Double = unsafeArray(vertsNum - 2)
  final def vLastY: Double = unsafeArray(vertsNum - 1)

  /** The last vertex. The default convention places this just anti clockwise of 12 oclock. */
  def vLast: Pt2 = vLastX pp vLastY

  /** Polygon side 0 from vertex 0 to vertex 1. */
  final def side0: LineSeg = LineSeg(vLast, v0)

  /** The X component of the centre or half way point of side 0 of this polygon. */
  final def sd0CenX: Double = vLastX aver v0x

  /** The Y component of the centre or half way point of side 0 of this polygon. */
  final def sd0CenY: Double = vLastY aver v0y

  /** The centre or half way point of side 0 of this polygon. Side 0 starts at the vertex v0 and ends at the vertex v1. This can be thought of as
   *  vertex 0.5. */
  final def sd0Cen: Pt2 = sd0CenX pp sd0CenY

  /** Performs the side effecting function on the [[Pt2]] value of each vertex. */
  final override def vertsForeach[U](f: Pt2 => U): Unit = ssForeach(f)

  /** Performs the side effecting function on the [[Pt2]] value of each vertex. */
  final def vertsTailForeach[U](f: Pt2 => U): Unit = ssTailForeach(f)

  /** Foreach vertex excluding vertex 1, perform the side effecting function on the Tuple2 of the x and y values of the vertex. For maximum efficiency
   * override the implementation in sub classes. */
  final def vertPairsTailForeach[U](f: (Double, Double) => U): Unit = vertsTailForeach(v => f(v.x, v.y))

  /** A function that takes a 2D geometric transformation on a [[Pt2]] as a parameter and performs the transformation on all the vertices returning a
   * new transformed Polygon */
  def vertsTrans(f: Pt2 => Pt2): Polygon = vertsMap(f).toPolygon

  override def vertsIForeach[U](f: (Int, Pt2) => U): Unit =
  { var count = 0
    vertsForeach{ v =>
      f(count, v)
      count += 1
    }
  }

  override def vertsFold[B](init: B)(f: (B, Pt2) => B): B =
  { var res = init
    vertsForeach(v => res = f(res, v))
    res
  }

  override def vertsMap[B, ArrB <: Arr[B]](f: Pt2 => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  { val acc = build.newBuff()
    vertsForeach{ v => build.buffGrow(acc, f(v)) }
    build.buffToSeqLike(acc)
  }

  /** flatMap to an immutable Arr. */
  def vertsFlatMap[BB <: Arr[_]](f: Pt2 => BB)(implicit build: ArrFlatBuilder[BB]): BB =
  { val buff: build.BuffT = build.newBuff()
    vertsForeach(v => build.buffGrowArr(buff, f(v)))
    build.buffToSeqLike(buff)
  }

  /** flatMap with index to an immutable Arr. */
  def vertsIFlatMap[BB <: Arr[_]](iInit: Int = 0)(f: (Pt2, Int) => BB)(implicit build: ArrFlatBuilder[BB]): BB =
  { val buff: build.BuffT = build.newBuff()
    var count: Int = iInit
    vertsForeach { v =>
      val newElems = f(v, count)
      build.buffGrowArr(buff, newElems)
      count += 1
    }
    build.buffToSeqLike(buff)
  }

  def unsafeNegX: Array[Double] = unsafeD1Map(d => -d)
  def unsafeNegY: Array[Double] = unsafeD2Map(d => -d)

  /** Returns the vertex of the given index. Throws if the index is out of range, if it less than 1 or greater than the number of vertices. */
  final def unsafeVert(index: Int): Pt2 = ssIndex(index)

  def dropVert(v: Int): Polygon =
  { val res = PolygonGen.uninitialised(vertsNum - 1)
    iUntilForeach(v){i => res.setElemUnsafe(i, vert(i)) }
    iUntilForeach(v + 1, vertsNum){i => res.setElemUnsafe(i - 1, vert(i)) }
    res
  }

  @inline def side(index: Int): LineSeg = LineSeg(vert(index), ife(index == vertsNum - 1, v0, vert(index + 1)))

  /** foreachs over the sides or edges of the Polygon These are of type [[LineSeg]]. */
  override def sidesForeach[U](f: LineSeg => U): Unit =
  { var count = 0
    while (count < vertsNum) { f(side(count)); count += 1 }
  }

  /** foreachs over the sides or edges of the Polygon These are of type [[LineSeg]]. */
  def iForeachSide(initCount: Int = 0)(f: (LineSeg, Int) => Unit): Unit =
  { var count = 0
    while (count < vertsNum)
    { f(side(count), count + initCount)
      count += 1
    }
  }

  /** maps over the sides or edges of the Polygon These are of type [[LineSeg]]. */
  def sidesMap[A, AA <: Arr[A]](f: LineSeg => A)(implicit build: ArrMapBuilder[A, AA]): AA =
  { var count = 0
    val res = build.uninitialised(vertsNum)
    while (count < vertsNum)
    { res.setElemUnsafe(count, f(side(count + 1)))
      count += 1
    }
    res
  }

  /** maps with a integer counter over the sides or edges of the Polygon These are of type [[LineSeg]]. */
  def sidesIMap[A, AA <: Arr[A]](initCount: Int = 0)(f: (LineSeg, Int) => A)(implicit build: ArrMapBuilder[A, AA]): AA =
  { var count = 0
    val res = build.uninitialised(vertsNum)
    while (count < vertsNum)
    { res.setElemUnsafe(count, f(side(count + 1), count + initCount))
      count += 1
    }
    res
  }

  /** maps with a integer counter over the sides or edges of the Polygon These are of type [[LineSeg]]. */
  def sidesIFlatMap[AA <: Arr[_]](initCount: Int = 0)(f: (LineSeg, Int) => AA)(implicit build: ArrFlatBuilder[AA]): AA =
  { var count = initCount
    val buff = build.newBuff()
    sidesForeach { side =>
      val newElems = f(side, count)
      build.buffGrowArr(buff, newElems)
      count += 1
    }
    build.buffToSeqLike(buff)
  }

  def sidesFold[A](init: A)(f: (A, LineSeg) => A): A =
  { var acc = init
    sidesForeach{ s => acc = f(acc, s) }
    acc
  }

  override def attribs: RArr[XANumeric] = ???
  override def fill(fillColour: Colour): PolygonFill = PolygonFill(this, fillColour)
  override def fillInt(intValue: Int): PolygonFill = PolygonFill(this, Colour(intValue))
  override def draw(lineColour: Colour = Black, lineWidth: Double = 2): PolygonDraw = PolygonDraw(this, lineWidth, lineColour)

  override def fillDraw(fillColour: Colour, lineColour: Colour, lineWidth: Double): PolygonCompound =
    PolygonCompound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)))

  /** Returns the X component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
  def xVert(index: Int): Double = unsafeArray(index * 2)

  /** Returns the Y component of the vertex of the given number. Will throw an exception if the vertex index is out of range. For maximum efficiency
   * override the implementation in sub classes. */
  def yVert(index: Int): Double = unsafeArray(index * 2 +1)

  /** The X component of vertex v0, will throw on a 0 vertices polygon. */
  final def v0x: Double = unsafeArray(0)

  /** The Y component of vertex v1, will throw on a 0 vertices polygon.  */
  final def v0y: Double = unsafeArray(1)

  /** Vertex v0, will throw on a 0 vertices polygon. By convention the default position for this vertex is at the top or 12 o'clock position of the
   * polygon or the vertex immediately anti clockwise if there is no vertex in this position. */
  final def v0: Pt2 = v0x pp v0y

  /** Currently throws, not sure if that is the correct behaviour. Creates a bounding rectangle for a collection of 2d points */
  override def boundingRect: BoundingRect =
  { var minX, maxX = v0x
    var minY, maxY = v0y
    vertsTailForeach{v =>
      minX = minX.min(v.x)
      maxX = maxX.max(v.x)
      minY = minY.min(v.y)
      maxY = maxY.max(v.y)
    }
    BoundingRect(minX, maxX, minY, maxY)
  }

  @inline def polygonMap(f: Pt2 => Pt2): Polygon = vertsMap(f).toPolygon

  /** Translate geometric transformation on a Polygon returns a Polygon. The return type of this method will be narrowed  further in most descendant
   *  traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slateXY(xDelta: Double, yDelta: Double): Polygon = polygonMap(_.addXY(xDelta, yDelta))

  /** Translate geometric transformation on a Polygon returns a Polygon. The return type of this method will be narrowed further in most descendant
   *  traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  def slate(offset: Vec2Like): Polygon = polygonMap(_.slate(offset))

  /** Uniform scaling against both X and Y axes transformation on a polygon returning a Polygon. Use the xyScale method for differential scaling. The
   *  return type of this method will be narrowed further in descendant traits / classes. */
  override def scale(operand: Double): Polygon = polygonMap(_.scale(operand))

  /** Mirror, reflection transformation of a Polygon across the X axis, returns a Polygon. */
  override def negY: Polygon = polygonMap(_.negY)

  /** Mirror, reflection transformation of Polygon across the Y axis, returns a Polygon. */
  override def negX: Polygon = polygonMap(_.negX)

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): Polygon = polygonMap(_.prolign(matrix))

  override def rotate90: Polygon = polygonMap(_.rotate90)
  override def rotate180: Polygon = polygonMap(_.rotate180)
  override def rotate270: Polygon = polygonMap(_.rotate270)

  /** Rotation 2D geometric transformation on a Polygon, taking the rotation as a scalar measured in radians, returns a Polygon. The Return type will
   *  be narrowed in some but not all sub traits / classes. */
  override def rotate(angle: AngleVec): Polygon = polygonMap(_.rotate(angle))

  /** Reflect 2D geometric transformation across a line, line segment or ray on a polygon, returns a Polygon. The Return type will be narrowed in sub
   *  traits / classes. */
  override def reflect(lineLike: LineLike): Polygon = polygonMap(_.reflect(lineLike))

  /** XY scaling 2D geometric transformation on a Polygon returns a Polygon. This allows different scaling factors across X and Y dimensions. The
   *  return type will be narrowed in some, but not all descendant Polygon types. */
  override def scaleXY(xOperand: Double, yOperand: Double): Polygon = polygonMap(_.xyScale(xOperand, yOperand))

  /** Shear 2D geometric transformation along the X Axis on a Polygon, returns a Polygon. The return type will be narrowed in some but not all sub
   *  classes and traits. */
  override def shearX(operand: Double): Polygon = polygonMap(_.xShear(operand))

  /** Shear 2D geometric transformation along the Y Axis on a Polygon, returns a Polygon. The return type will be narrowed in sub classes and
   *  traits. */
  override def shearY(operand: Double): Polygon = polygonMap(_.xShear(operand))

  /** Determines if the parameter point lies inside this Polygon. */
  def ptInside(pt: Pt2): Boolean =
  { val num = sidesFold(0)((acc, line) => acc + ife(line.rayIntersection(pt), 1, 0))
    num.isOdd
  }
  /** The centre point of this Polygon. The default centre for Polygons is the centre of the bounding rectangle. */
  def cenPt: Pt2 = boundingRect.cen
  def cenVec: Vec2 = boundingRect.cenVec

  def sline(index: Int): LineSeg =
  { val startVertNum: Int = ife(index == 1, vertsNum, index - 1)
    LineSeg(vert(startVertNum), vert(index))
  }

  def active(id: AnyRef): PolygonActive = PolygonActive(this, id)
  def activeChildren(id: AnyRef, children: GraphicElems): PolygonCompound = PolygonCompound(this, RArr(), active(id) %: children)

  def fillActive(fillColour: Colour, pointerID: AnyRef): PolygonCompound =
    PolygonCompound(this, RArr(fillColour), RArr(PolygonActive(this, pointerID)))

  def drawActive(lineColour: Colour = Black, lineWidth: Double = 2, pointerID: AnyRef): PolygonCompound =
    PolygonCompound(this, RArr(DrawFacet(lineColour, lineWidth)), RArr(PolygonActive(this, pointerID)))

  /** Creates a PolygonCompound graphic that is active with a simple 1 colour fill and has a draw graphic for the Polygon. The default values for the
   * draw area line width of 2 and a colour of Black. */
  def fillActiveDraw(fillColour: Colour, pointerID: AnyRef, lineColour: Colour = Black, lineWidth: Double = 2): PolygonCompound =
    PolygonCompound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)), RArr(PolygonActive(this, pointerID)))

  def fillDrawActive(fillColour: Colour, pointerID: AnyRef, lineWidth: Double, lineColour: Colour = Black): PolygonCompound =
    PolygonCompound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)), RArr(PolygonActive(this, pointerID)))

  def fillDrawText(fillColour: Colour, str: String, fontSize: Int = 24, lineColour: Colour = Black, lineWidth: Double = 2.0): PolygonCompound =
    PolygonCompound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)), RArr(TextGraphic(str, fontSize, cenDefault)))

  def parentFillText(pointerID: Any, fillColour: Colour, str: String, fontSize: Int = 10, textColour: Colour = Black, align: TextAlign = CenAlign):
  PolygonCompound = PolygonCompound(this, RArr(fillColour, TextFacet(str, textColour)), RArr())

  def fillDrawTextActive(fillColour: Colour, pointerID: AnyRef, str: String, fontSize: Int = 24, lineWidth: Double, lineColour: Colour = Black,
    align: TextAlign = CenAlign): PolygonCompound = PolygonCompound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)),
    RArr(TextGraphic(str, fontSize, cenDefault, Black, align), PolygonActive(this, pointerID)))

  def fillText(fillColour: Colour, str: String, fontSize: Int = 10, textColour: Colour = Black, layer: Int = 0): PolygonCompound =
    PolygonCompound(this, RArr(fillColour), RArr(TextGraphic(str, fontSize, cenDefault, textColour)))

  def fillTextActive(fillColour: Colour, pointerEv: AnyRef, str: String, fontSize: Int = 24, fontColour: Colour = Black, align: TextAlign = CenAlign):
    PolygonCompound = PolygonCompound(this, RArr(fillColour), RArr(PolygonActive(this, pointerEv), TextGraphic(str, fontSize, cenDefault, fontColour, align)))

  /** Insert vertex. */
  def insVert(insertionPoint: Int, newVec: Pt2): Polygon =
  { val res = PolygonGen.uninitialised(vertsNum + 1)
    iUntilForeach(insertionPoint - 1){ i => res.setElemUnsafe(i, vert(i)) }
    res.setElemUnsafe(insertionPoint, newVec)
    iUntilForeach(insertionPoint, vertsNum){ i => res.setElemUnsafe(i + 1, vert(i)) }
    res
  }

  /** Insert vertices before the specified insertion vertex. */
  def insVerts(insertionPoint: Int, newVecs: Pt2 *): Polygon =
  { val res = PolygonGen.uninitialised(vertsNum + newVecs.length)
    iUntilForeach(insertionPoint){ i => res.setElemUnsafe(i, vert(i)) }
    newVecs.iForeach((i, elem) => res.setElemUnsafe(insertionPoint + i, elem))
    iUntilForeach(insertionPoint, vertsNum){ i => res.setElemUnsafe(i + newVecs.length, vert(i)) }
    res
  }

  def precisionDefault: Double = ???

  override def approx(that: Any, precision: Double): Boolean = ???
}

/** Companion object for the Polygon trait, contains factory apply methods and implicit instances for all 2D affine geometric transformations. */
object Polygon
{
  def apply(pts: Pt2 *): Polygon = PolygonGen(pts: _*)

  def uninitialised(length: Int): Polygon = new PolygonGen(new Array[Double](length * 2))

  implicit val arrBuildImplicit: ArrMapBuilder[Polygon, PolygonArr] = new ArrayDblArrMapBuilder[Polygon, PolygonArr]
  { override type BuffT = PolygonBuff
    override def fromArrayArrayDbl(array: Array[Array[Double]]): PolygonArr = new PolygonArr(array)
    override def newBuff(length: Int): PolygonBuff = PolygonBuff(length)
  }

  implicit val eqImplicit: EqT[Polygon] = (p1, p2) => EqT.arrayImplicit[Double].eqT(p1.unsafeArray, p2.unsafeArray)

  implicit val slateImplicit: Slate[Polygon] = (obj: Polygon, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[Polygon] = (obj: Polygon, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Polygon] = (obj: Polygon, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[Polygon] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: ScaleXY[Polygon] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val reflectImplicit: Reflect[Polygon] = (obj: Polygon, lineLike: LineLike) => obj.reflect(lineLike)

  implicit val reflectAxesImplicit: TransAxes[Polygon] = new TransAxes[Polygon]
  { override def negYT(obj: Polygon): Polygon = obj.negY
    override def negXT(obj: Polygon): Polygon = obj.negX
    override def rotate90(obj: Polygon): Polygon = obj.rotate90
    override def rotate180(obj: Polygon): Polygon = obj.rotate180
    override def rotate270(obj: Polygon): Polygon = obj.rotate270
  }
  
  implicit val shearImplicit: Shear[Polygon] = new Shear[Polygon]
  { override def shearXT(obj: Polygon, yFactor: Double): Polygon = obj.shearX(yFactor)
    override def shearYT(obj: Polygon, xFactor: Double): Polygon = obj.shearY(xFactor)
  }
}