/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import geom._, egrid._

/** An Earth grid covering a full 30 degree range of longitude for non-polar regions with a hex span of 120Km */
abstract class EGrid120Long(rBottomCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGridLong(rBottomCen, cenLongInt, 35.kiloMetres, 200, rowArray) with EGrid120Sys

object EGrid120Long
{
  def reg(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, leftC: Int, rightC: Int): EGrid120Long =
  { val array = new Array[Int](rTopCen - rBottomCen + 2)
    val bot = rBottomCen.roundUpToEven
    val top = rTopCen.roundDownToEven
    iToForeach(bot, top, 2){r =>
      val (lc, rc) = ife(r.div4Rem0, (leftC.roundUpTo(_.div4Rem0), rightC.roundDownTo(_.div4Rem0)),
        (leftC.roundUpTo(_.div4Rem2), rightC.roundDownTo(_.div4Rem2)))
      array(r - rBottomCen) = (rc - lc + 4) / 4
      array(r - rBottomCen + 1) = lc
    }
    new EGrid120LongPart(rBottomCen, rTopCen, cenLongInt, array)
  }
}

class EGrid120LongPart(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGrid120Long(rBottomCen, cenLongInt, rowArray) with EGridLongPart
{
  override val fullGrid: EGrid120LongFull = EGrid120LongFull.fullBounds(cenLongInt)
}

/** A main non-polar grid with a hex span of 120Km */
class EGrid120LongFull(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends EGridLongFull(rBottomCen, rTopCen, cenLongInt, 30.kiloMetres, 200)

object EGrid120LongFull
{ def apply(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) = new EGrid120LongFull(rBottomCen, rTopCen, cenLongInt)

  val fullBounds: Array[EGrid120LongFull] =
  { val array = new Array[EGrid120LongFull](12)
    iUntilForeach(0, 12) { i => array(i) = EGrid120LongFull(40, 160, i) }
    array
  }
}

/** Terrain data grid for [[EGrid120LongFull]]s. */
trait Long120Terrs extends LongTerrs
{ override implicit val grid: EGrid120LongFull
}