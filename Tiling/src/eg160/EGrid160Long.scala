/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import egrid._, geom._, pglobe._, prid.phex._

/** An Earth grid covering a full 30 degree range of longitude for non-polar regions with a hex span of 320Km */
abstract class EGrid160Long(rBottomCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGridLong(rBottomCen, cenLongInt, 40.kMetres, 200, rowArray)

object EGrid160Long
{
  def reg(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, leftC: Int, rightC: Int): EGrid160Long =
  { val array = new Array[Int](rTopCen - rBottomCen + 2)
    val bot = rBottomCen.roundUpToEven
    val top = rTopCen.roundDownToEven
    iToForeach(bot, top, 2){r =>
      val (lc, rc) = ife(r.div4Rem0, (leftC.roundUpTo(_.div4Rem0), rightC.roundDownTo(_.div4Rem0)),
        (leftC.roundUpTo(_.div4Rem2), rightC.roundDownTo(_.div4Rem2)))
      array(r - rBottomCen) = (rc - lc + 4) / 4
      array(r - rBottomCen + 1) = lc
    }
    new EGrid160LongPart(rBottomCen/*, rTopCen*/, cenLongInt, array)
  }
}

/** A main non-polar grid with a hex span of 160Km */
class EGrid160LongFull(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridLongFull(rBottomCen, rTopCen, cenLongInt, 40.kMetres, 200)

object EGrid160LongFull
{ def apply(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) = new EGrid160LongFull(rBottomCen, rTopCen, cenLongInt)
}

class EGrid160LongPart(rBottomCen: Int, cenLongInt: Int, rArray: Array[Int]) extends
  EGrid160Long(rBottomCen, cenLongInt, /*40.kMetres,*/ rArray)
{
  /** The latitude and longitude [[LatLong]] of an [[HCoord]] within the grid. */
  override def hCoordLL(hc: HCoord): LatLong = hCoordMiddleLL(hc)
}

/** Terrain data grid for [[EGrid320LongFull]]s. */
trait Long160Terrs extends LongTerrs
{ override implicit val grid: EGrid160LongFull
}