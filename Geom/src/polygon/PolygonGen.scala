/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, pWeb._

/** The implementation class for a general [[Polygon]] as opposed to a specific [[Polygon]] such as a [[Square]] or a [[Rectangle]], is encoded as a
 *  sequence of plain 2 dimension (mathematical) vectors. Minimum length 3. Clockwise is the default. Polygon may be altered to include a centre. */
final class PolygonGen(val arrayUnsafe: Array[Double]) extends Polygon with Pt2sLike with AffinePreserve with DataDbl2s[Pt2]
{ override type ThisT = PolygonGen
  override def vert(index: Int): Pt2 = indexData(index - 1)
  @inline override def foreachVertPairTail[U](f: (Double, Double) => U): Unit = dataForeachPairTail(f)
  override def unsafeFromArray(array: Array[Double]): PolygonGen = new PolygonGen(array)
  @inline override def vertsArray: Array[Double] = arrayUnsafe
  override def typeStr: String = "Polygon"
  override def vertsNum: Int = arrayUnsafe.length / 2 //- dblsNumOffset
  override def fill(fillColour: Colour): PolygonFill = PolygonFill(this, fillColour)
  override def draw(lineColour: Colour = Black, lineWidth: Double = 2): PolygonDraw = PolygonDraw(this, lineWidth, lineColour)
  @inline override def polygonMap(f: Pt2 => Pt2): PolygonGen = vertsMap(f).toPolygon
  override def xVert(index: Int): Double = arrayUnsafe(index * 2)// + dblsNumOffset)
  override def yVert(index: Int): Double = arrayUnsafe(index * 2 + 1)// + dblsNumOffset)
  @inline def v1x: Double = arrayUnsafe(0)// + dblsNumOffset)
  @inline def v1y: Double = arrayUnsafe(1)// + dblsNumOffset)
  @inline def v1: Pt2 = v1x pp v1y
  override def vertsTrans(f: Pt2 => Pt2): PolygonGen = new PolygonGen(arrTrans(f))

  /** A method to perform all the [[ProlignPreserve]] transformations with a function from PT2 => PT2. */
  @inline override def ptsTrans(f: Pt2 => Pt2): PolygonGen = vertsTrans(f)

  override def vertsForeach[U](f: Pt2 => U): Unit =iUntilForeach(0, arrayUnsafe.length, 2){ i =>
    f(Pt2(arrayUnsafe(i), arrayUnsafe(i + 1))); ()
  }

  override def foreachVertTail[U](f: Pt2 => U): Unit = iUntilForeach(2, arrayUnsafe.length, 2){ i =>
    f(Pt2(arrayUnsafe(i), arrayUnsafe(i + 1))); ()
  }

  override def attribs: Arr[XANumeric] = ???

  override def canEqual(that: Any): Boolean = that match {
    case s: Shape => true
    case _ => false
  }

  def eq(obj: PolygonGen): Boolean = arrayUnsafe.sameElements(obj.arrayUnsafe)
  def minX: Double = dataTailfold(v1.x)((acc, el) => acc.min(el.x))
  def maxX: Double = dataTailfold(v1.x)((acc, el) => acc.max(el.x))
  def minY: Double = dataTailfold(v1.y)((acc, el) => acc.min(el.y))
  def maxY: Double = dataTailfold(v1.y)((acc, el) => acc.max(el.y))
  def width: Double = maxX - minX
  def height: Double = maxY - minY

  override def vertsArrayX: Array[Double] =
  { val newArray = new Array[Double](vertsNum)
    iUntilForeach(0, vertsNum){i => newArray(i) = xVert(i)}
    newArray
  }

  override def vertsArrayY: Array[Double] =
  { val newArray = new Array[Double](vertsNum)
    iUntilForeach(0, vertsNum){i => newArray(i) = yVert(i)}
    newArray
  }

  /** Insert vertex. */
  override def insVert(insertionPoint: Int, newVec: Pt2): PolygonGen =
  { val res = PolygonGen.uninitialised(elemsNum + 1)
    (0 until insertionPoint).foreach(i => res.unsafeSetElem(i, indexData(i)))
    res.unsafeSetElem(insertionPoint, newVec)
    (insertionPoint until elemsNum).foreach(i => res.unsafeSetElem(i + 1, indexData(i)))
    res
  }

  /** Insert vertices */
  override def insVerts(insertionPoint: Int, newVecs: Pt2 *): PolygonGen =
  { val res = PolygonGen.uninitialised(elemsNum + newVecs.length)
    (0 until insertionPoint).foreach(i => res.unsafeSetElem(i, indexData(i)))
    newVecs.iForeach((i, elem) => res.unsafeSetElem(insertionPoint + i, elem))
    (insertionPoint until elemsNum).foreach(i => res.unsafeSetElem(i + newVecs.length, indexData(i)))
    res
  }

  def distScale(distRatio: Length): PolygonMetre = map[PtMetre2, PolygonMetre](p => p.toMetres(distRatio))
}

/** Companion object for [[PolygonGen]]. */
object PolygonGen extends DataDbl2sCompanion[Pt2, PolygonGen]
{ override def fromArrayDbl(array: Array[Double]): PolygonGen = new PolygonGen(array)

  implicit val eqImplicit: EqT[PolygonGen] = (p1, p2) => EqT.arrayImplicit[Double].eqv(p1.arrayUnsafe, p2.arrayUnsafe)

  implicit val persistImplicit: DataDbl2sPersist[Pt2, PolygonGen] = new DataDbl2sPersist[Pt2, PolygonGen]("Polygon")
  { override def fromArray(value: Array[Double]): PolygonGen = new PolygonGen(value)

    override def showT(obj: PolygonGen, way: Show.Way, maxPlaces: Int, minPlaces: Int): String = ???
  }
}