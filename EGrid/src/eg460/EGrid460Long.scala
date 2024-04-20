/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import geom._, egrid._

/** An Earth grid covering a full 30 degree range of longitude for non-polar regions with a hex span of 460Km */
abstract class EGrid460Long(rBottomCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGridLong(rBottomCen, cenLongInt, 160.kMetres, 100, rowArray) with EGrid460Sys

object EGrid460Long
{
  def reg(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, leftC: Int, rightC: Int): EGrid460Long =
  { val array = new Array[Int](rTopCen - rBottomCen + 2)
    val bot = rBottomCen.roundUpToEven
    val top = rTopCen.roundDownToEven
    iToForeach(bot, top, 2){r =>
      val (lc, rc) = ife(r.div4Rem0, (leftC.roundUpTo(_.div4Rem0), rightC.roundDownTo(_.div4Rem0)),
        (leftC.roundUpTo(_.div4Rem2), rightC.roundDownTo(_.div4Rem2)))
      array(r - rBottomCen) = (rc - lc + 4) / 4
      array(r - rBottomCen + 1) = lc
    }
    new EGrid460LongPart(rBottomCen, rTopCen, cenLongInt, array)
  }
}

class EGrid460LongPart(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGrid460Long(rBottomCen, cenLongInt, rowArray) with EGridLongPart
{
  override val fullGrid: EGrid460LongFull = EGrid460LongFull.fullBounds(cenLongInt)
}

/** A main non-polar grid with a hex span of 460Km */
class EGrid460LongFull(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridLongFull(rBottomCen, rTopCen, cenLongInt, 115.kMetres, 100)

object EGrid460LongFull
{ def apply(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) = new EGrid460LongFull(rBottomCen, rTopCen, cenLongInt)

  val fullBounds: Array[EGrid460LongFull] = {
    val array = new Array[EGrid460LongFull](12)
    iUntilForeach(0, 12) { i => array(i) = EGrid460LongFull(40, 160, i) }
    array
  }
}

/** Terrain data grid for [[EGrid460LongFull]]s. */
trait Long460Terrs extends LongTerrs
{ override implicit val grid: EGrid460LongFull
}