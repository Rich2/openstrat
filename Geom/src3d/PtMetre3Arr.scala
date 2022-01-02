/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** Collection class for [[Pt3]]s. Only use this if the more specific [[PolygonM]] and[[LinePathMs]] classes are not appropriate. */
class PtMetre3Arr(val unsafeArray: Array[Double]) extends AnyVal with ArrDbl3s[PtM3]
{ type ThisT = PtMetre3Arr
  def unsafeFromArray(array: Array[Double]): ThisT = new PtMetre3Arr(array)
  override def typeStr: String = "Metres3s"
  override def fElemStr: PtM3 => String = _ => "Undefined" //_.str
  override def dataElem(d1: Double, d2: Double, d3: Double): PtM3 = new PtM3(d1, d2, d3)

  /** This methods function is to work on a sequence of 3d points representing a polygon on the surface a globe (eg the Earth). If Z is positive its
   *  on the side of the Earth that the viewer is looking at. Returns z positive dist2 points if 1 or more of the points are z positive. Z negative
   *  points are moved to the horizon. */
  def earthZPositive: OptEither[PtMetre2Arr, CurveSegDists] =
  {
    existsCount(_.z.pos) match
    { case 0 => NoOptEither
    case n if n == dataLength => SomeA(map(_.xy))
    case n => NoOptEither
      //      {
      //        var els: List[Either[Dist2, Dist2]] = lMap {
      //          case el if el.z.pos => Right(el.xy)
      //          case el =>
      //          { val xy = el.xy
      //            val fac = xy.magnitude / EarthAvRadius
      //            Left(xy / fac)
      //          }
      //        }
      //        while (els.head.isLeft && els.last.isLeft && els.init.last.isLeft) els = els.init
      //
      //        val els2: List[Either[Dist2, Dist2]] = els.drop(2).foldLeft(els.take(2))((acc, el) => el match
      //          {
      //            case Left(v) if acc.last.isLeft && acc.init.last.isLeft => acc.init :+ el
      //            case el => acc :+ el
      //          })
      //
      //        val acc: CurveSegDists = CurveSegDists.factory(els2.length)// List[CurveSegDist] = Nil
      //        var last: Either[Dist2, Dist2] = els2.last
      //        els2.iForeach {(e, i) =>
      //          e match
      //          { case Right(d2) => acc.setElem(i, LineSegDist(d2))
      //            case Left(d2) if last.isLeft => acc.setElem(i, ArcSegDist(Dist2Z, d2))
      //            case Left(d2) => acc.setElem(i, LineSegDist(d2))
      //          }
      //          last = e
      //        }
      //        GlobedSome(acc)
      //      }
    }
  }
}

object PtMetre3Arr extends DataDbl3sCompanion[PtM3, PtMetre3Arr]
{ override def fromArrayDbl(array: Array[Double]): PtMetre3Arr = new PtMetre3Arr(array)

  implicit val flatBuilderImplicit: Dbl3sArrFlatBuilder[PtM3, PtMetre3Arr] = new Dbl3sArrFlatBuilder[PtM3, PtMetre3Arr]
  { type BuffT = BuffPtMetre3
    override def fromDblArray(array: Array[Double]): PtMetre3Arr = new PtMetre3Arr(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): BuffPtMetre3 = new BuffPtMetre3(inp)
  }
}

/** A specialised flat ArrayBuffer[Double] based class for [[Pt3]]s collections. */
final class BuffPtMetre3(val unsafeBuff: ArrayBuffer[Double]) extends AnyVal with BuffDbl3s[PtM3]
{ override def typeStr: String = "BuffPtMetre3"
  def dblsToT(d1: Double, d2: Double, d3: Double): PtM3 = new PtM3(d1, d2, d3)
}

object BuffPtMetre3
{
  def apply(initSize: Int = 4): BuffPtMetre3 = new BuffPtMetre3(new Buff[Double](initSize * 3))
}