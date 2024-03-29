/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** [[HGrid]] implementation class for the general case where the rows have different lengths and irregular start row coordinates. This is backed by
 *  an Array[Int]. The length of this Array is twice the number of tile rows in the grid. Each row from lowest to highest has two values length of the
 *  row in the number of tile centres [[HCen]]s and the cTileMin coordinate for the row.
 * @constructor creates a new HexGridIrr with a defined grid.
 * @param bottomCenR The r value for the bottom tile row of the TileGrid.
 * @param tileRowsStartEnd the Array contains 2 values per Tile Row, the cStart Tile and the cEnd Tile */
class HGridGen(val bottomCenR: Int, val unsafeRowsArray: Array[Int]) extends HGrid with TellSeqLike[HCenRow]
{ override def typeStr: String = "HGridIrr"

  unsafeRowsArray.foreach(i => if (i.isOdd) excep("A bound is odd. " + unsafeRowsArray.mkString(" ")))

  override def evA: Show[HCenRow] = HCenRow.showEv

  override def tellForeach(f: HCenRow => Unit): Unit = allRowsForeach(f)

  final val numTileRows: Int = unsafeRowsArray.length / 2

  def canEqual(a: Any): Boolean = a.isInstanceOf[HGridSys]

  override def equals(that: Any): Boolean = that match
  { case that: HGridGen => canEqual(that) && bottomCenR ==  that.bottomCenR && unsafeRowsArray.sameElements(that.unsafeRowsArray)
    case _ => false
  }

  override def sepTileLtOpt(hSide: HSep): Option[HCen] =
  { val ot: HCen = sepTileLtUnsafe(hSide)
    ife(hCenExists(ot), Some(ot), None)
  }

  override def sepTileLtAndVertUnsafe(hSide: HSep): (HCen, Int) = hSide.tileLtAndVert

  final override def topCenR: Int = bottomCenR + numTileRows * 2 - 2

  /** Combine adjacent tiles of the same value. */
  override def adjTilesOfTile(origR: Int, origC: Int): HCenArr =
  { val buff = HCenBuff()
    val r = origR
    val c = origC
    if (r < topCenR) if ((c + 2 >= rowLeftCenC(r + 2)) & (c + 2 <= rowRightCenC(r + 2)) ) buff.growInts(r + 2, c + 2)
    if (c + 4 <= rowRightCenC(r)) buff.growInts(r, c + 4)
    if (r > bottomCenR) {
      if ((c + 2 >= rowLeftCenC(r - 2)) & (c + 2 <= rowRightCenC(r - 2))) buff.growInts(r - 2, c + 2)
      if ((c - 2 >= rowLeftCenC(r - 2)) & (c - 2 <= rowRightCenC(r - 2))) buff.growInts(r - 2, c - 2)
    }
    if (c - 4 >= rowLeftCenC(r)) buff.growInts(r, c - 4)
    if (r < topCenR) if ((c - 2 >= rowLeftCenC(r + 2)) & (c - 2 <= rowRightCenC(r + 2)) ) buff.growInts(r + 2, c - 2)
    HCenArr.fromBuff(buff)
  }

  /** The [[HCenOrSep]] coordinate centre for this hex grid. */
  override def coordCen: HCoord = HCoord(rCen, cCen)

  override def rowForeachSep(r: Int)(f: HSep => Unit): Unit = r match
  { case r if r.isEven =>
    { rowForeach(r){ hc => f(HSep(hc.r, hc.c -2)) }
      if (rowNumTiles(r) > 0) f(HSep(r, rowRightCenC(r) + 2))
    }

    case r if r == bottomSepR => rowForeach(r + 1){ hc => f(HSep(r, hc.c - 1)); f(HSep(r, hc.c + 1)) }
    case r if r == topSepR => rowForeach(r - 1){ hc => f(HSep(r, hc.c - 1)); f(HSep(r, hc.c + 1)) }

    case r =>
    { val start = rowLeftCenC(r - 1).min(rowLeftCenC(r + 1)) - 1
      val end = rowRightCenC(r - 1).max(rowRightCenC(r + 1)) + 1
      iToForeach(start, end, 2){ c => f(HSep(r, c)) }
    }
  }

  override def innerRowForeachInnerSide(r: Int)(f: HSep => Unit): Unit = r match
  { case r if r.isEven => iToForeach(rowLeftCenC(r) + 2, rowRightCenC(r) -2, 4){ c => f(HSep(r, c)) }
    case r if r == bottomSepR =>
    case r if r == topSepR =>

    case r =>
    { val start = rowLeftCenC(r - 1).max(rowLeftCenC(r + 1)) - 1
      val end = rowRightCenC(r - 1).min(rowRightCenC(r + 1)) + 1
      iToForeach(start, end, 2){ c => f(HSep(r, c)) }
    }
  }

  override def edgesForeach(f: HSep => Unit): Unit = if (numTileRows > 0)
  {
    if(rowNumTiles(bottomCenR) > 0) iToForeach(rowLeftCenC(bottomCenR) - 1, rowRightCenC(bottomCenR) + 1, 2)(c => f(HSep(bottomSepR, c)))
    iToForeach(bottomCenR, topCenR){r => r match{
      case r if r.isEven => { f(HSep(r, rowLeftCenC(r) -2)); f(HSep(r, rowRightCenC(r) + 2)) }
      case r => {
        val bLeft = rowLeftCenC(r - 1)
        val uLeft = rowLeftCenC(r + 1)
        val leftStart = bLeft.min(uLeft) - 1
        val leftEnd = bLeft.max(uLeft) - 3
        iToForeach(leftStart, leftEnd, 2){c => f(HSep(r, c)) }
        val bRight = rowRightCenC(r - 1)
        val uRight = rowRightCenC(r + 1)
        val rightEnd = bRight.max(uRight) + 1
        val rightStart = bRight.min(uRight) + 3
        iToForeach(rightStart, rightEnd, 2){c => f(HSep(r, c)) }
      }
    }}
    if(rowNumTiles(topCenR) > 0) iToForeach(rowLeftCenC(topCenR) - 1, rowRightCenC(topCenR) + 1, 2)(c => f(HSep(topSepR, c)))
  }

  def cSideRowMin(r: Int): Int = ???

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   * Array data. */
  override def sepLayerArrayIndex(r: Int, c: Int): Int =
  { val cDelta = r match
    { case r if r == topSepR => c - rowLeftCenC(r - 1) + 1
      case r if r == bottomSepR => c - rowLeftCenC(r + 1) + 1
      case r if r.isEven => (c - rowLeftCenC(r) + 2) / 2
      case r => c - rowLeftCenC(r - 1).min(rowLeftCenC(r + 1)) + 1
    }
    val ic = cDelta / 2
    sepRowIndexArray(r - bottomSepR) + ic
  }

  override def gridLeftCenC: Int = foldRows(Int.MaxValue - 1)((acc, r) => acc.min(rowLeftCenC(r)))
  override def gridRightCenC: Int = foldRows(Int.MinValue )((acc, r) => acc.max(rowRightCenC(r)))

  override def numRow0s: Int = numTileRows.ifMod(bottomCenR.div4Rem0, _.roundUpToEven) / 2
  override def numRow2s: Int = numTileRows.ifMod(bottomCenR.div4Rem2, _.roundUpToEven) / 2

  override def layerArrayIndex(r: Int, c: Int): Int =
  { val wholeRows = iUntilIntSum(bottomCenR, r, 2){ ri => rowNumTiles(ri) }
    wholeRows + (c - rowLeftCenC(r)) / 4
  }

  override def rowNumTiles(row: Int): Int = ((rowRightCenC(row) - rowLeftCenC(row)) / 4 + 1).max0

  /** Foreachs over each tile centre of the specified row applying the side effecting function to the [[HCen]]. */
  def rowForeach(r: Int)(f: HCen => Unit): Unit = iToForeach(rowLeftCenC(r), rowRightCenC(r), 4){ c => f(HCen(r, c))}

  /** The start (or by default left column) of the tile centre of the given row. Will throw on illegal values. */
  override def rowLeftCenC(row: Int): Int = row match
  { case r if r.isOdd => excep(s"$r is odd number which is illegal for a tile row in tileRowStart method.")
    case r if r > topCenR =>
      excep(s"Row number $r is greater than top tile row $topCenR. There are $numTileRows rows. Exception in tileRowStart method.")
    case r if r < bottomCenR => excep(s"$r Row number less than bottom tile row in tileRowStart method.")
    case r =>{
      val c = unsafeRowsArray(row - bottomCenR)
      if (c.isOdd) excep(s"$c is invalid in method rowleftCenC for row $r")
      c
    }
  }

  /** The end (or by default right) column number of the tile centre of the given row. Will throw on illegal values. */
  override def rowRightCenC(row: Int): Int = row match
  { case r if r.isOdd => excep(s"$r is odd number which is illegal for a tile row in tileRowEnd method.")
    case r if r > topCenR => excep(s"Row number $r is greater than top tile row $topCenR in tileRowEnd method.")
    case r if r < bottomCenR => excep(s"$r Row number less than $bottomCenR, the bottom tile row value in tileRowEnd method.")
    case _ => unsafeRowsArray(row - bottomCenR + 1)// rowLeftCenC(row) + (rowNumTiles(row) - 1) * 4
  }

  override def hCenExists(r: Int, c: Int): Boolean = r match
  { case r if r > topCenR => false
    case r if r < bottomCenR => false
    case r => c >= rowLeftCenC(r) & c <= rowRightCenC(r)
  }
}

object HGridGen
{
  /** Takes the top row number followed by pairs of start and end c numbers. */
  def fromTop(rMax: Int, cMinMaxs: (Int, Int) *): HGridGen =
  { val numRows: Int = cMinMaxs.length
    val array = new Array[Int](numRows * 2)
    val rMin = rMax - (numRows - 1) * 2
    iUntilForeach(numRows){ i =>
      val (cMin, cMax) = cMinMaxs(numRows - 1 - i)
      array(i * 2) = cMin
      array(i * 2 + 1) = cMax
    }
    new HGridGen(rMin, array)
  }

  implicit val showEv: ShowSeqLike[HCenRow, HGridGen] = ShowSeqLike[HCenRow, HGridGen]("HGridIrr", (obj, f) => obj.allRowsForeach(f))

  implicit val unshowEv: UnshowFromArr[HCenRow, HCenRowArr, HGridGen] =
  { val f: HCenRowArr => HGridGen = { ra =>
      val array = new Array[Int](ra.length * 2)
      ra.iForeach{ (i, hr) =>
        array(2 * i) = hr.cStart
        array(2 * i + 1) = hr.cEnd
      }
      new HGridGen(ra.arrayUnsafe(0), array)
    }
    new UnshowFromArr[HCenRow, HCenRowArr, HGridGen]("HGridIrr", f)
  }
}