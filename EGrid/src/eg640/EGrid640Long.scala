/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid.phex._, egrid._, pEarth._

/** An Earth grid covering a full 30 degree range of longitude for non-polar regions with a hex span of 640Km */
abstract class EGrid640Long(rBottomCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGridLong(rBottomCen, cenLongInt, 160.kMetres, 100, rowArray) with EGrid640Sys

object EGrid640Long
{
  def reg(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, leftC: Int, rightC: Int): EGrid640Long =
  { val array = new Array[Int](rTopCen - rBottomCen + 2)
    val bot = rBottomCen.roundUpToEven
    val top = rTopCen.roundDownToEven
    iToForeach(bot, top, 2){r =>
      val (lc, rc) = ife(r.div4Rem0, (leftC.roundUpTo(_.div4Rem0), rightC.roundDownTo(_.div4Rem0)),
        (leftC.roundUpTo(_.div4Rem2), rightC.roundDownTo(_.div4Rem2)))
      array(r - rBottomCen) = (rc - lc + 4) / 4
      array(r - rBottomCen + 1) = lc
    }
    new EGrid640LongPart(rBottomCen, rTopCen, cenLongInt, array)
  }
}

class EGrid640LongPart(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGrid640Long(rBottomCen, cenLongInt, rowArray) with EGridLongPart
{
  override val fullGrid: EGrid640LongFull = EGrid640LongFull.fullBounds(cenLongInt)
}

/** A main non-polar grid with a hex span of 640Km */
class EGrid640LongFull(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridLongFull(rBottomCen, rTopCen, cenLongInt, 160.kMetres, 100)

object EGrid640LongFull
{ def apply(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) = new EGrid640LongFull(rBottomCen, rTopCen, cenLongInt)

  val fullBounds: Array[EGrid640LongFull] = {
    val array = new Array[EGrid640LongFull](12)
    iUntilForeach(0, 12) { i => array(i) = EGrid640LongFull(40, 160, i) }
    array
  }
}

/** Terrain data grid for [[EGrid640LongFull]]s. */
trait Long640Terrs extends LongTerrs
{ override implicit val grid: EGrid640LongFull
}