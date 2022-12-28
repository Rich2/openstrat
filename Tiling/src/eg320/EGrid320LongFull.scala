/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._

/** An Earth grid covering a full 30 degree range of longitude for non-polar regions with a hex span of 320Km */
abstract class EGrid320Long(rBottomCen: Int, cenLongInt: Int, rowArray: Array[Int]) extends
  EGridLong(rBottomCen, cenLongInt, 80.kMetres, 100, rowArray)

object EGrid320Long {
  def reg(rBottomCen: Int, rTopCen: Int, cenLongInt: Int, leftC: Int, rightC: Int): EGrid320Long = {
    val array = new Array[Int](rTopCen - rBottomCen + 2)
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

/** object for creating earth grids with 320km hexs, with a c scale of 80km. */
object EGrid320
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */

  def e0(rBottomCen: Int = 130, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 0)
  def e30(rBottomCen: Int = 130, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen,  1)
  def e60(rBottomCen: Int = 130, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 2)
  def e90(rBottomCen: Int = 130, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 3)
  def e120(rBottomCen: Int = 130, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 4)
  def e150(rBottomCen: Int = 130, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 5)
  def e180(rBottomCen: Int = 130, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 6)
  def w150(rBottomCen: Int = 130, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 7)
  def w120(rBottomCen: Int = 130, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 8)
  def w90(rBottomCen: Int = 130, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 9)
  def w60(rBottomCen: Int = 130, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 10)
  def w30(rBottomCen: Int = 130, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen,11)

  def scen0: EScenBasic =
  { val grid: EGridLongFull = e0(130)
    EScenBasic(grid, Terr320E0.terrs, Terr320E0.sTerrs, "320km 0E")
  }

  def scen1: EScenBasic =
  { val grid: EGridLongFull = e30(130)
    EScenBasic(grid, Terr320E30.terrs, Terr320E30.sTerrs, "320km 30E")
  }

  def scen2: EScenBasic =
  { val grid: EGridLongFull = e60(130)
    EScenBasic(grid, Terr320E60.terrs, Terr320E60.sTerrs, "320km 60E")
  }

  def scen3: EScenBasic =
  { val grid: EGridLongFull = e90(130)
    EScenBasic(grid, Terr320E90.terrs, Terr320E90.sTerrs, "320km 90E")
  }

  def scen4: EScenBasic =
  { val grid: EGridLongFull = e120(130)
    EScenBasic(grid, Terr320E120.terrs, Terr320E120.sTerrs, "320km 120E")
  }

  def scen5: EScenBasic =
  { val grid: EGridLongFull = e150(130)
    EScenBasic(grid, Terr320E150.terrs, Terr320E150.sTerrs, "320km 150E")
  }

  def scen6: EScenBasic =
  { val grid: EGridLongFull = e180(130)
    EScenBasic(grid, Terr320E180.terrs, Terr320E180.sTerrs, "320km 180E")
  }

  def scen7: EScenBasic =
  { val grid: EGridLongFull = w150(130)
    EScenBasic(grid, Terr320W150.terrs, Terr320W150.sTerrs, "320km 150W")
  }

  def scen8: EScenBasic =
  { val grid: EGridLongFull = w120(130)
    EScenBasic(grid, Terr320W120.terrs, Terr320W120.sTerrs, "320km 120W")
  }

  def scen9: EScenBasic =
  { val grid: EGridLongFull = w90(130)
    EScenBasic(grid, Terr320W90.terrs, Terr320W90.sTerrs, "320km 90W")
  }

  def scen10: EScenBasic =
  { val grid: EGridLongFull = w60(130)
    EScenBasic(grid, Terr320W60.terrs, Terr320W60.sTerrs, "320km 60W")
  }

  def scen11: EScenBasic =
  { val grid: EGridLongFull = w30(130)
    EScenBasic(grid, Terr320W30.terrs, Terr320W30.sTerrs, "320km 30W")
  }
}