/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*, Colour.Black

/** A mathematical simple closed polygon, with at least 1 vertex. The general case can be instantiated with [[PolygonGen]], but it provides the interface for
 * particular sub sets of polygons such as triangles and square. Mathematically a closed polygon made up of straight line segments. The default convention is to
 * number the vertices in a clockwise direction, with vertex 1 the first vertex that is clockwise from 12 O'Clock. Sides are numbered in a corresponding manner
 * with then end point of side sd((n - 1) at vertex 0. */
trait Polygon extends Any, Shape, BoundedElem, Approx[Double], Pt2SeqSpec, PolygonLikeDbl2[Pt2]
{ type ThisT <: Polygon
  override type SideT = LineSeg

  def rightX: Double = verts.foldLeft(v0x)((acc, pt) => acc.max(pt.x))

  /** The X component of vertex v0, will throw on a 0 vertices polygon. */
  def v0x: Double = arrayUnsafe(0)

  /** The Y component of vertex v1, will throw on a 0 vertices polygon. */
  def v0y: Double = arrayUnsafe(1)

  /** Vertex v0, will throw on a 0 vertices polygon. By convention the default position for this vertex is at the top or 12 o'clock position of the polygon or
   * the vertex immediately anti-clockwise if there is no vertex in this position. */
  def v0: Pt2 = Pt2(v0x, v0y)

  def vLastX: Double = ???//arrayUnsafe(numVerts - 2)
  def vLastY: Double = ???//arrayUnsafe(numVerts - 1)

  /** The last vertex. The default convention places this just anti-clockwise of 12 o'clock. */
  def vLast: Pt2 = ???//Pt2(vLastX, vLastY)

  /** Polygon side 0 from vertex 0 to vertex 1. */
  def side0: LineSeg = LineSeg(v0, vert(1))

  /** The X component of the centre or halfway point of side 0 of this polygon. */
  def sd0CenX: Double = v0x \/ vertX(1)

  /** The Y component of the centre or halfway point of side 0 of this polygon. */
  def sd0CenY: Double = v0y \/ vertY(1)

  /** The centre or halfway point of side 0 of this polygon. Side 0 starts at the vertex v0 and ends at the vertex v1. This can be thought of as vertex 0.5. */
  def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)

  override def verts: Pt2Arr =
  { val newArray: Array[Double] = new Array[Double](numVerts * 2)
    var i = 0
    while (i < numVerts)
    { newArray(i * 2) = vertX(i)
      newArray(i * 2 + 1) = vertY(i)
      i += 1
    }
    new Pt2Arr(newArray)
  }

  /** Performs the side effecting function on the [[Pt2]] value of each vertex. */
  final override def vertsForeach[U](f: Pt2 => U): Unit = foreach(f)

  /** A function that takes a 2D geometric transformation on a [[Pt2]] as a parameter and performs the transformation on all the vertices returning a new
   * transformed [[Polygon]]. */
  def vertsTrans(f: Pt2 => Pt2): Polygon = new PolygonGen(arrTrans(f))

  override def vertsMap[B, ArrB <: Arr[B]](f: Pt2 => B)(implicit build: BuilderMapArr[B, ArrB]): ArrB =
  { val acc = build.uninitialised(numVerts)
    var i = 0
    vertsForeach{ v => acc.setElemUnsafe(i, f(v)); i += 1 }
    acc
  }

  def unsafeNegX: Array[Double] = arrayD1Map(d => -d)
  def unsafeNegY: Array[Double] = arrayD2Map(d => -d)

  /** Returns the vertex of the given index. Throws if the index is out of range, if it less than 1 or greater than the number of vertices. */
  final def unsafeVert(rawIndex: Int): Pt2 = elem(rawIndex)

  def dropVert(v: Int): Polygon =
  { val res = PolygonGen.uninitialised(numVerts - 1)
    iUntilForeach(v){i => res.setElemUnsafe(i, vert(i)) }
    iUntilForeach(v + 1, numVerts){ i => res.setElemUnsafe(i - 1, vert(i)) }
    res
  }

  /** Returns the X component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
  def vertX(index: Int): Double = arrayUnsafe(index * 2)

  /** Returns the Y component of the vertex of the given number. Will throw an exception if the vertex index is out of range. For maximum efficiency override
   * the implementation in subclasses. */
  def vertY(index: Int): Double = arrayUnsafe(index * 2 + 1)

  @inline override def side(index: Int): LineSeg = LineSeg(vert(index), vert(index + 1))

  override def sides: LineSegArr = new LineSegArr(arrayForSides)

  override def sidesForeach[U](f: LineSeg => U): Unit =
  { var i = 0
    while (i < numVerts) { f(side(i)); i += 1 }
  }

  /** maps over the sides or edges of the Polygon These are of type [[LineSeg]]. */
  def sidesMap[A, AA <: Arr[A]](f: LineSeg => A)(implicit build: BuilderMapArr[A, AA]): AA =
  { var i: Int = 0
    val res = build.uninitialised(numVerts)
    while (i < numVerts)
    { res.setElemUnsafe(i, f(side(i)))
      i += 1
    }
    res
  }

  /** Currently throws, not sure if that is the correct behaviour. Creates a bounding rectangle for a collection of 2d points. */
  override def boundingRect: Rect =
  { var minX, maxX = v0x
    var minY, maxY = v0y
    verts.tailForeach{v =>
      minX = minX.min(v.x)
      maxX = maxX.max(v.x)
      minY = minY.min(v.y)
      maxY = maxY.max(v.y)
    }
    Rect.lrbt(minX, maxX, minY, maxY)
  }

  override def boundingWidth: Double =
  { var minX, maxX = v0x
    verts.tailForeach { v =>
      minX = minX.min(v.x)
      maxX = maxX.max(v.x)
    }
    maxX - minX
  }

  override def boundingHeight: Double =
  { var minY, maxY = v0y
    verts.tailForeach { v =>
      minY = minY.min(v.y)
      maxY = maxY.max(v.y)
    }
    maxY - minY
  }

  override def slate(operand: VecPt2): Polygon = PolygonGen(arraySlate(operand))
  override def slate(xOperand: Double, yOperand: Double): Polygon = PolygonGen(arraySlateXY(xOperand, yOperand))
  override def slateX(xOperand: Double): Polygon = PolygonGen(arraySlateX(xOperand))
  override def slateY(yOperand: Double): Polygon = PolygonGen(arraySlateY(yOperand))
  override def scale(operand: Double): Polygon = PolygonGen(arrayScale(operand))
  override def negX: Polygon = PolygonGen(arrayNegX)
  override def negY: Polygon = PolygonGen(arrayNegY)
  override def prolign(matrix: ProlignMatrix): Polygon = PolygonGen(arrayProlign(matrix))
  override def rotate90: Polygon = PolygonGen(arrayRotate90)
  override def rotate180: Polygon = PolygonGen(arrayRotate180)
  override def rotate270: Polygon = PolygonGen(arrayRotate270)
  override def rotate(rotation: AngleVec): Polygon = PolygonGen(arrayRotate(rotation))
  override def reflect(lineLike: LineLike): Polygon = PolygonGen(arrayReflect(lineLike))
  override def scaleXY(xOperand: Double, yOperand: Double): Polygon = PolygonGen(arrayScaleXY(xOperand, yOperand))
  override def shearX(operand: Double): Polygon = map(_.xShear(operand))
  override def shearY(operand: Double): Polygon = map(_.xShear(operand))

  def sidesFold[A](init: A)(f: (A, LineSeg) => A): A =
  { var acc: A = init
    sidesForeach{ s => acc = f(acc, s) }
    acc
  }

  /** Determines if the parameter point lies inside this Polygon. */
  def ptInside(pt: Pt2): Boolean =
  { val num = sidesFold(0)((acc, line) => acc + ife(line.rayIntersection(pt), 1, 0))
    num.isOdd
  }
  /** The centre point of this Polygon. The default centre for Polygons is the centre of the bounding rectangle. */
  def cenPt: Pt2 = boundingRect.cen
  def cenVec: Vec2 = boundingRect.cenVec

  def sline(index: Int): LineSeg =
  { val startVertNum: Int = ife(index == 1, numVerts, index - 1)
    LineSeg(vert(startVertNum), vert(index))
  }

  def active(id: AnyRef): PolygonActive = PolygonActive(this, id)
  def activeChildren(id: AnyRef, children: GraphicElems): PolygonCompound = PolygonCompound(this, RArr(), active(id) %: children)

  override def fill(fillfacet: FillFacet): PolygonFill = PolygonFill(this, fillfacet)
  override def fillInt(intValue: Int): PolygonFill = PolygonFill(this, Colour(intValue))
  override def draw(lineWidth: Double = 2, lineColour: Colour = Black): PolygonDraw = PolygonDraw(this, lineWidth, lineColour)

  override def fillDraw(fillColour: Colour, lineColour: Colour = Black, lineWidth: Double = 2): PolygonCompound =
    PolygonCompound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)))

  def fillActive(fillColour: Colour, pointerID: Any): PolygonCompound =
    PolygonCompound(this, RArr(fillColour), RArr(PolygonActive(this, pointerID)))

  def drawActive(lineColour: Colour = Black, lineWidth: Double = 2, pointerID: Any): PolygonCompound =
    PolygonCompound(this, RArr(DrawFacet(lineColour, lineWidth)), RArr(PolygonActive(this, pointerID)))

  /** Creates a PolygonCompound graphic that is active with a simple 1 colour fill and has a draw graphic for the Polygon. The default values for the draw area
   * line width of 2 and a colour of Black. */
  def fillActiveDraw(fillColour: Colour, pointerID: Any, lineColour: Colour = Black, lineWidth: Double = 2): PolygonCompound =
    PolygonCompound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)), RArr(PolygonActive(this, pointerID)))

  def fillDrawText(fillColour: Colour, str: String, fontSize: Double = 24, lineColour: Colour = Black, lineWidth: Double = 2.0): PolygonCompound =
    PolygonCompound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)), RArr(TextFixed(str, fontSize, cenDefault)))

  def fillActiveDrawText(fillColour: Colour, pointerID: Any, str: String, fontSize: Double = 24, lineWidth: Double, lineColour: Colour = Black,
    textColour: Colour = Black, align: TextAlign = CenAlign): PolygonCompound = PolygonCompound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)),
    RArr(TextFixed(str, fontSize, cenDefault, textColour, align), PolygonActive(this, pointerID)))

  /** Returns compound polygon graphic with fill and Text members. The element parameters being entered in name order. */
  def fillTextAbs(fillColour: Colour, str: String, fontSize: Double = 10, textColour: Colour = Black, layer: Int = 0): PolygonCompound =
    PolygonCompound(this, RArr(fillColour), RArr(TextFixed(str, fontSize, cenDefault, textColour)))

  /** Returns compound polygon graphic with fill, active and Text members. The element parameters being entered in name order. */
  def fillActiveTextAbs(fillColour: Colour, pointerEv: Any, str: String, fontSize: Double = 24, fontColour: Colour = Black, align: TextAlign = CenAlign):
    PolygonCompound = PolygonCompound(this, RArr(fillColour), RArr(PolygonActive(this, pointerEv), TextFixed(str, fontSize, cenDefault, fontColour, align)))

  def fillActiveTextlign(fillColour: Colour, pointerEv: Any, str: String, fontSize: Double, fontColour: Colour = Black, align: TextAlign = CenAlign):
  PolygonCompound = PolygonCompound(this, RArr(fillColour), RArr(PolygonActive(this, pointerEv), Textlign(str, fontSize, cenDefault, fontColour, align)))

  override def fillActiveText(fillColour: Colour, pointerEv: Any, str: String, fontRatio: Double, fontColour: Colour = Black,
    align: TextAlign = CenAlign, baseLine: BaseLine = BaseLine.Middle, minSize: Double = 6): PolygonCompound =
    PolygonCompound(this, RArr(fillColour, TextFacet(str, fontRatio, fontColour, align, baseLine, minSize)), RArr(PolygonActive(this, pointerEv)))

  /** Text centred and sized from the bounding rectangle. */
  def textSized(str: String, colour: Colour = Black): TextFixed =
  { val fontSize: Double = boundingWidth / (str.longestLineLen + 1)
    TextFixed(str, fontSize, boundingCen, colour)
  }

  /** Insert vertex. */
  def insVert(insertionPoint: Int, newVec: Pt2): Polygon =
  { val res = PolygonGen.uninitialised(numVerts + 1)
    iUntilForeach(insertionPoint - 1){ i => res.setElemUnsafe(i, vert(i)) }
    res.setElemUnsafe(insertionPoint, newVec)
    iUntilForeach(insertionPoint, numVerts){ i => res.setElemUnsafe(i + 1, vert(i)) }
    res
  }

  /** Insert vertices before the specified insertion vertex. */
  def insVerts(insertionPoint: Int, newPts: Pt2 *): Polygon =
  { val newLen = numVerts + newPts.length
    val res = PolygonGen.uninitialised(newLen)
    iUntilForeach(insertionPoint){ i => res.setElemUnsafe(i, vert(i)) }
    newPts.iForeach((i, elem) => res.setElemUnsafe(insertionPoint + i, elem))
    iUntilForeach(insertionPoint, numVerts){ i => res.setElemUnsafe(i + newPts.length, vert(i)) }
    res
  }

  /** Insert vertices as [[Double]]s before the specified insertion vertex. */
  def insVertDbls(insertionPoint: Int, newPts: Double*): Polygon =
  { val exLen = newPts.length
    ifExcep(exLen.isOdd, "Odd number of Doubles supplied for Pt2 insertion.")
    val newArray = new Array[Double](arrayLen + exLen)
    val inp2 = (insertionPoint %% numVerts) * 2
    Array.copy(arrayUnsafe, 0, newArray, 0, inp2)
    Array.copy(newPts.toArray, 0, newArray, inp2, exLen)
    val remLen = arrayLen - inp2
    Array.copy(arrayUnsafe, inp2, newArray, inp2 + exLen, remLen)
    Polygon.fromArray(newArray)
  }

  def precisionDefault: Double = ???

  override def approx(that: Any, precision: Double): Boolean = ???

  /** The SVG points attributes for this polygon. */
  def pointsAttrib: XmlAtt =
  { val vertStr: String = verts.foldLeft((acc, v) => acc -- v.x.str + "," + (-v.y).str)
    XmlAtt("points", vertStr)
  }

  /** The SVG attributes for this polygon. */
  override def attribs: RArr[XmlAtt] = RArr(pointsAttrib)

  /** Increase the number of vertices and [[LineSeg]]s by breaking up the [[LineSeg]]s into parts. */
  def vertsMultiply(n: Int): Polygon = if (n < 2) this else
  { val res = Polygon.uninitialised(numVerts * n)
    iUntilForeach(numVerts){ i =>
      val ls: LineSeg = vert(i).lineSegTo(vert(i + 1))
      iUntilForeach(n) { j => res.setElemUnsafe(i * n + j, ls.fractionalPoint(j.toDouble / n)) }
    }
    res
  }

  def inRect(ratio: Double): Rect =
  { val bd: Rect = boundingRect
    val cens = bd.spacedPts(5, 5)
    cens.foldLeft{(acc, cen) =>
      if (bd.ptInside(cen))
      { val newRect = inRectFrom(cen, ratio)
        ife(newRect.widthHeightMin(ratio) > acc.widthHeightMin(ratio), newRect, acc)
      }
      else acc
    }
  }

  /** Approximation for an inner rectangle given a starting centre. */
  def inRectFrom(startCen: Pt2, ratio: Double): Rect =
  { val cen = startCen
    val cx = startCen.x
    val cy = startCen.y
    val initMargin = 0.8
    val multi = 4
    val fOrder: (Pt2, Pt2) => Boolean = (p1, p2) => (p1.x - cx).abs + (p1.y - cy).abs < (p2.x - cx).abs + (p2.y - cy).abs
    val verts2: Pt2Arr = vertsMultiply(multi).verts
    val verts3: Pt2Arr = verts2.sortBy(fOrder)

    val bounds: Rect = boundingRect
    var left: Double = bounds.left
    var right: Double = bounds.right
    var bottom: Double = bounds.bottom
    var top: Double = bounds.top

    verts3.foreach{
      case vt if (cen >> vt).isTopRight && vt.x < right && vt.y < top => if ((vt.x - cx) > (vt.y - cy) * ratio) right = vt.x else top = vt.y
      case vt if (cen >> vt).isBottomRight && vt.x < right && vt.y > bottom => if ((vt.x - cx) > (cy - vt.y) * ratio) right = vt.x else bottom = vt.y
      case vt if (cen >> vt).isTopleft && vt.x > left && vt.y < top => if ((cx - vt.x) > (vt.y - cy) * ratio) left = vt.x else top = vt.y
      case vt if (cen >> vt).isBottomLeft && vt.x > left && vt.y > bottom => if ((cx - vt.x) > (cy - vt.y) * ratio) left = vt.x else bottom = vt.y
      case _ =>
    }

    Rect.lrbt(left, right, bottom, top)
  }
}

/** Companion object for the Polygon trait, contains factory apply methods and implicit instances for all 2D affine geometric transformations. */
object Polygon extends CompanionSlDbl2[Pt2, Polygon]
{ override def fromArray(array: Array[Double]): Polygon = new PolygonGen(array)

  /** Implicit [[EqT]] type class instance / evidence for [[Polygon]]. */
  implicit val eqTEv: EqT[Polygon] = (p1, p2) => p1.arrayUnsafe.sameElements(p2.arrayUnsafe)

  /** Implicit [[Slate]] type class instance / evidence for [[Polygon]]. */
  implicit val slateEv: Slate[Polygon] = (obj, operand) => obj.slate(operand)

  /** Implicit [[SlateXY]] type class instance / evidence for [[Polygon]]. */
  implicit val slateXYEv: SlateXY[Polygon] = (obj: Polygon, dx: Double, dy: Double) => obj.slate(dx, dy)

  /** Implicit [[Scale]] type class instance / evidence for [[Polygon]]. */
  implicit val scaleEv: Scale[Polygon] = (obj: Polygon, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[Polygon]]. */
  implicit val rotateEv: Rotate[Polygon] = (obj: Polygon, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance / evidence for [[Polygon]]. */
  implicit val prolignEv: Prolign[Polygon] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[Polygon]]. */
  implicit val scaleXYEv: ScaleXY[Polygon] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[Reflect]] type class instance / evidence for [[Polygon]]. */
  implicit val reflectEv: Reflect[Polygon] = (obj: Polygon, lineLike: LineLike) => obj.reflect(lineLike)

  /** Implicit [[TransAxes]] type class instance / evidence for [[Polygon]]. */
  implicit val transAxesEv: TransAxes[Polygon] = new TransAxes[Polygon]
  { override def negYT(obj: Polygon): Polygon = obj.negY
    override def negXT(obj: Polygon): Polygon = obj.negX
    override def rotate90(obj: Polygon): Polygon = obj.rotate90
    override def rotate180(obj: Polygon): Polygon = obj.rotate180
    override def rotate270(obj: Polygon): Polygon = obj.rotate270
  }

  /** Implicit [[Shear]] type class instance / evidence for [[Polygon]]. */
  implicit val shearEv: Shear[Polygon] = new Shear[Polygon]
  { override def shearXT(obj: Polygon, yFactor: Double): Polygon = obj.shearX(yFactor)
    override def shearYT(obj: Polygon, xFactor: Double): Polygon = obj.shearY(xFactor)
  }

  /** Implicit [[Drawing]] type class instance / evidence for [[Polygon]]. */
  implicit val drawerEv: Drawing[Polygon, PolygonDraw] = (obj, lineWidth, colour) => obj.draw(lineWidth, colour)
}