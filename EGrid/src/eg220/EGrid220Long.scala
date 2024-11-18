/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import geom._, egrid._

/** An Earth grid covering a full 30 degree range of longitude for non-polar regions with a hex span of 220Km */
abstract class EGrid220Long(rBottomCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGridLong(rBottomCen, cenLongInt, 55.kiloMetres, 100, rowArray) with EGrid220Sys

object EGrid220Long
{
  def reg(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, leftC: Int, rightC: Int): EGrid220Long =
  { val array = new Array[Int](rTopCen - rBottomCen + 2)
    val bot = rBottomCen.roundUpToEven
    val top = rTopCen.roundDownToEven
    iToForeach(bot, top, 2){r =>
      val (lc, rc) = ife(r.div4Rem0, (leftC.roundUpTo(_.div4Rem0), rightC.roundDownTo(_.div4Rem0)),
        (leftC.roundUpTo(_.div4Rem2), rightC.roundDownTo(_.div4Rem2)))
      array(r - rBottomCen) = lc
      array(r - rBottomCen + 1) = rc
    }
    new EGrid220LongPart(rBottomCen, rTopCen, cenLongInt, array)
  }
}

class EGrid220LongPart(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGrid220Long(rBottomCen, cenLongInt, rowArray) with EGridLongPart
{
  override val fullGrid: EGrid220LongFull = EGrid220LongFull.fullBounds(cenLongInt)
}

/** A main non-polar grid with a hex span of 220Km */
class EGrid220LongFull(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridLongFull(rBottomCen, rTopCen, cenLongInt, 55.kiloMetres, 100)

object EGrid220LongFull
{ def apply(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) = new EGrid220LongFull(rBottomCen, rTopCen, cenLongInt)

  val fullBounds: Array[EGrid220LongFull] = {
    val array = new Array[EGrid220LongFull](12)
    iUntilForeach(0, 12) { i => array(i) = EGrid220LongFull(40, 202, i) }
    array
  }
}

/** Terrain data grid for [[EGrid220LongFull]]s. */
trait Long220Terrs extends LongTerrs
{ override implicit val grid: EGrid220LongFull
}