/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid.phex._, egrid._, pEarth._

/** An Earth grid covering a full 30 degree range of longitude for non-polar regions with a hex span of 640Km */
abstract class EGridMegaLong(rBottomCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGridLong(rBottomCen, cenLongInt, 160.kMetres, 100, rowArray) with EGridMegaSys

object EGridMegaLong
{
  def reg(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, leftC: Int, rightC: Int): EGridMegaLong =
  { val array = new Array[Int](rTopCen - rBottomCen + 2)
    val bot = rBottomCen.roundUpToEven
    val top = rTopCen.roundDownToEven
    iToForeach(bot, top, 2){r =>
      val (lc, rc) = ife(r.div4Rem0, (leftC.roundUpTo(_.div4Rem0), rightC.roundDownTo(_.div4Rem0)),
        (leftC.roundUpTo(_.div4Rem2), rightC.roundDownTo(_.div4Rem2)))
      array(r - rBottomCen) = (rc - lc + 4) / 4
      array(r - rBottomCen + 1) = lc
    }
    new EGridMegaLongPart(rBottomCen, rTopCen, cenLongInt, array)
  }
}

class EGridMegaLongPart(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGridMegaLong(rBottomCen, cenLongInt, rowArray) with EGridLongPart
{
  override val fullGrid: EGridMegaLongFull = EGridMegaLongFull.fullBounds(cenLongInt)
}

/** A main non-polar grid with a hex span of MegaKm */
class EGridMegaLongFull(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridLongFull(rBottomCen, rTopCen, cenLongInt, 250.kMetres, 100)

object EGridMegaLongFull
{ def apply(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) = new EGridMegaLongFull(rBottomCen, rTopCen, cenLongInt)

  val fullBounds: Array[EGridMegaLongFull] = {
    val array = new Array[EGridMegaLongFull](12)
    iUntilForeach(0, 12) { i => array(i) = EGridMegaLongFull(40, 160, i) }
    array
  }
}

/** Terrain data grid for [[EGridMegaLongFull]]s. */
trait LongMegaTerrs extends LongTerrs
{ override implicit val grid: EGridMegaLongFull
}