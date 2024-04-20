/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import geom._, egrid._

/** An Earth grid covering a full 30 degree range of longitude for non-polar regions with a hex span of 640Km */
abstract class EGrid13Long(rBottomCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGridLong(rBottomCen, cenLongInt, 325.kMetres, 100, rowArray) with EGrid13Sys

object EGrid13Long
{
  def reg(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, leftC: Int, rightC: Int): EGrid13Long =
  { val array = new Array[Int](rTopCen - rBottomCen + 2)
    val bot = rBottomCen.roundUpToEven
    val top = rTopCen.roundDownToEven
    iToForeach(bot, top, 2){r =>
      val (lc, rc) = ife(r.div4Rem0, (leftC.roundUpTo(_.div4Rem0), rightC.roundDownTo(_.div4Rem0)),
        (leftC.roundUpTo(_.div4Rem2), rightC.roundDownTo(_.div4Rem2)))
      array(r - rBottomCen) = (rc - lc + 4) / 4
      array(r - rBottomCen + 1) = lc
    }
    new EGrid13LongPart(rBottomCen, rTopCen, cenLongInt, array)
  }
}

class EGrid13LongPart(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGrid13Long(rBottomCen, cenLongInt, rowArray) with EGridLongPart
{
  override val fullGrid: EGrid13LongFull = EGrid13LongFull.fullBounds(cenLongInt)
}

/** A main non-polar grid with a hex span of 1300Km */
class EGrid13LongFull(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridLongFull(rBottomCen, rTopCen, cenLongInt, 325.kMetres, 100)

object EGrid13LongFull
{ def apply(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) = new EGrid13LongFull(rBottomCen, rTopCen, cenLongInt)

  val fullBounds: Array[EGrid13LongFull] =
  { val array = new Array[EGrid13LongFull](12)
    iUntilForeach(0, 12) { i => array(i) = EGrid13LongFull(40, 118, i) }
    array
  }
}

/** Terrain data grid for [[EGrid13LongFull]]s. */
trait Long13Terrs extends LongTerrs
{ override implicit val grid: EGrid13LongFull
}