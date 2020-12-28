/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black, pWeb._

/** The implementation class for a general [[Polygon]] as opposed to a specific [[Polygon]] such as a [[Square]] or a [[Rectangle]], is encoded as a
 *  sequence of plain 2 dimension (mathematical) vectors. Minimum length 3. Clockwise is the default. Polygon may be altered to include a centre. */
final class PolygonImp(val arrayUnsafe: Array[Double]) extends Polygon with Vec2sLikeProdDbl2 with AffinePreserve
{ type ThisT = PolygonImp

  def vert(index: Int): Pt2 = apply(index - 1)
  def foreachPt(f: Pt2 => Unit): Unit = iUntilForeach(0, arrayUnsafe.length, 2)(i => f(Pt2(arrayUnsafe(i), arrayUnsafe(i + 1))))

  override def foreachVert[U](f: Pt2 => U): Unit =iUntilForeach(dblsNumOffset, arrayUnsafe.length, 2){ i =>
    f(Pt2(arrayUnsafe(i), arrayUnsafe(i + 1))); ()
  }

  override def foreachVertTail[U](f: Pt2 => U): Unit = iUntilForeach(dblsNumOffset + 2, arrayUnsafe.length, 2){ i =>
    f(Pt2(arrayUnsafe(i), arrayUnsafe(i + 1))); ()
  }

  /** Temporary value to transition from the current data to one where the centre pt included at the start of the underlying Array. */
  val ptNumOffset: Int = 0

  /** Temporary value to transition from the current data to one where the centre pt included at the start of the underlying Array. */
  def dblsNumOffset: Int = ptNumOffset * 2

  def unsafeFromArray(array: Array[Double]): PolygonImp = new PolygonImp(array)

  @inline override def ptsArray: Array[Double] = arrayUnsafe
  override def typeStr: String = "Polygon"

  override def attribs: Arr[XANumeric] = ???

  override def canEqual(that: Any): Boolean = that match {
    case s: Shape => true
    case _ => false
  }

  override def vertsNum: Int = arrayUnsafe.length / 2 - dblsNumOffset

  override def productArity: Int = 1
  override def productElement(n: Int): Any = arrayUnsafe

  override def xVert(index: Int): Double = arrayUnsafe(index * 2 + dblsNumOffset)
  override def yVert(index: Int): Double = arrayUnsafe(index * 2 + 1 + dblsNumOffset)
  @inline def x1: Double = arrayUnsafe(0 + dblsNumOffset)
  @inline def y1: Double = arrayUnsafe(1 + dblsNumOffset)
  @inline def v1: Pt2 = x1 pp y1
  override def fTrans(f: Pt2 => Pt2): PolygonImp = new PolygonImp(arrTrans(f))
  def eq(obj: PolygonImp): Boolean = arrayUnsafe.sameElements(obj.arrayUnsafe)
  def minX: Double = foldTailLeft(head.x)((acc, el) => acc.min(el.x))
  def maxX: Double = foldTailLeft(head.x)((acc, el) => acc.max(el.x))
  def minY: Double = foldTailLeft(head.y)((acc, el) => acc.min(el.y))
  def maxY: Double = foldTailLeft(head.y)((acc, el) => acc.max(el.y))
  def width: Double = maxX - minX
  def height: Double = maxY - minY

  override def xVertsArray: Array[Double] =
  { val newArray = new Array[Double](vertsNum)
    iUntilForeach(0, vertsNum){i => newArray(i) = xVert(i)}
    newArray
  }

  override def yVertsArray: Array[Double] =
  { val newArray = new Array[Double](vertsNum)
    iUntilForeach(0, vertsNum){i => newArray(i) = yVert(i)}
    newArray
  }

  /** Insert vertex. */
  override def insVert(insertionPoint: Int, newVec: Pt2): PolygonImp =
  { val res = PolygonImp.factory(elemsLen + 1)
    (0 until insertionPoint).foreach(i => res.unsafeSetElem(i, apply(i)))
    res.unsafeSetElem(insertionPoint, newVec)
    (insertionPoint until elemsLen).foreach(i => res.unsafeSetElem(i + 1, apply(i)))
    res
  }

  /** Insert vertices */
  override def insVerts(insertionPoint: Int, newVecs: Pt2 *): PolygonImp =
  { val res = PolygonImp.factory(elemsLen + newVecs.length)
    (0 until insertionPoint).foreach(i => res.unsafeSetElem(i, apply(i)))
    newVecs.iForeach((elem, i) => res.unsafeSetElem(insertionPoint + i, elem))
    (insertionPoint until elemsLen).foreach(i => res.unsafeSetElem(i + newVecs.length, apply(i)))
    res
  }

  def distScale(distRatio: Metres): PolygonMs = pMap[Metres2, PolygonMs](p => p.toDist2(distRatio))

  override def foldLeft[B](initial: B)(f: (B, Pt2) => B): B = super.foldLeft(initial)(f)

  override def fill(fillColour: Colour): PolygonFill = PolygonFill(this, fillColour)
  override def draw(lineColour: Colour = Black, lineWidth: Double = 2): PolygonDraw = PolygonDraw(this, lineWidth, lineColour)

  @inline override def polygonMap(f: Pt2 => Pt2): PolygonImp = vertsMap(f).toPolygon
}

/** Companion object for [[PolygonImp]]. */
object PolygonImp
{ implicit val factory: Int => PolygonImp = i => new PolygonImp(new Array[Double](i * 2))

  def apply(v1: Pt2, v2: Pt2, v3: Pt2, tail: Pt2 *): PolygonImp =
  { val len = (3 + tail.length)
    val res = factory(len)
    res.unsafeSetElems(0, v1, v2, v3)
    res.unsafeSetElemSeq(3, tail)
    res
  }

  implicit val eqImplicit: Eq[PolygonImp] = (p1, p2) => Eq.arrayImplicit[Double].eqv(p1.arrayUnsafe, p2.arrayUnsafe)

  implicit val persistImplicit: ArrProdDbl2Persist[Pt2, PolygonImp] = new ArrProdDbl2Persist[Pt2, PolygonImp]("Polygon")
  { override def fromArray(value: Array[Double]): PolygonImp = new PolygonImp(value)
  }

  /*implicit val polygonsBuildImplicit: ArrBuild[PolygonGen, Polygons] = new ArrArrayDblBuild[PolygonGen, Polygons]
  {
    override type BuffT = PolygonBuff
    def fromArray(array: Array[Array[Double]]): Polygons = new Polygons(array)

    override def newBuff(length: Int):  BuffT = ???
  }*/
}