/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex._, egrid._, pEarth._

/** An Earth grid covering a full 30 degree range of longitude for non-polar regions with a hex span of 320Km */
abstract class EGrid320Long(rBottomCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGridLong(rBottomCen, cenLongInt, 80.kMetres, 100, rowArray) with EGrid320Sys

object EGrid320Long
{
  def reg(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, leftC: Int, rightC: Int): EGrid320Long =
  { val array = new Array[Int](rTopCen - rBottomCen + 2)
    val bot = rBottomCen.roundUpToEven
    val top = rTopCen.roundDownToEven
    iToForeach(bot, top, 2){r =>
      val (lc, rc) = ife(r.div4Rem0, (leftC.roundUpTo(_.div4Rem0), rightC.roundDownTo(_.div4Rem0)),
        (leftC.roundUpTo(_.div4Rem2), rightC.roundDownTo(_.div4Rem2)))
      array(r - rBottomCen) = (rc - lc + 4) / 4
      array(r - rBottomCen + 1) = lc
    }
    new EGrid320LongPart(rBottomCen, rTopCen, cenLongInt, array)
  }
}

class EGrid320LongPart(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGrid320Long(rBottomCen, cenLongInt, rowArray) with EGridLongPart
{
  override val fullGrid: EGrid320LongFull = EGrid320LongFull.fullBounds(cenLongInt)
}

/** A main non-polar grid with a hex span of 320Km */
class EGrid320LongFull(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridLongFull(rBottomCen, rTopCen, cenLongInt, 80.kMetres, 100)

object EGrid320LongFull
{ def apply(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) = new EGrid320LongFull(rBottomCen, rTopCen, cenLongInt)

  val fullBounds: Array[EGrid320LongFull] = {
    val array = new Array[EGrid320LongFull](12)
    iUntilForeach(0, 12) { i => array(i) = EGrid320LongFull(40, 160, i) }
    array
  }
}

/** Terrain data grid for [[EGrid320LongFull]]s. */
trait Long320Terrs extends LongTerrs
{ override implicit val grid: EGrid320LongFull
}