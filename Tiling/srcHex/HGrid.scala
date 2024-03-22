/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

/** A grid of hexs. The grid may be a regular rectangle of hexs or an irregular grid with variable length rows.
 *  @groupdesc SidesGroup Trait members that operate on the sides of the Hex Grid.
 *  @groupname SidesGroup Side Members
 *  @groupprio SidesGroup 1010 */
trait HGrid extends TGrid with HGridSys with Tell
{
  final override def left: Double = gridLeftCenC - 2
  final override def right: Double = gridRightCenC + 2
  final override def top: Double = topCenR * yRatio + 4.0/Sqrt3
  final override def bottom: Double = bottomCenR * yRatio - 4.0/Sqrt3

  final override def leftSideC: Int = gridLeftCenC - 2
  final override def rightSideC: Int = gridLeftCenC + 2
  def rowLen(r: Int): Int = ((rowRightCenC(r) - rowLeftCenC(r)) / 4).max0

  /** The number of tile centre rows where r %% 4 == 0.  */
  def numRow0s: Int

  /** The number of tile centre rows where r %% 4 == 2.  */
  def numRow2s: Int

  /** The number of tile centres in the given row. */
  def rowNumTiles(row: Int): Int

  def rows: HCenRowArr = allRsMap{ r => HCenRow(r, rowLeftCenC(r), rowRightCenC(r)) }

  def allRowsForeach(f: HCenRow => Unit): Unit = allRsforeach{ r => f(HCenRow(r, rowLeftCenC(r), rowRightCenC(r))) }

  /** Carries out the procedure function on each [[HCen]] hex tile centre coordinate in the given tile row. This method is defined here rather than on
   *  TileGrid so it can take the specific narrow [[HCen]] parameter to the foreach function. */
  def rowForeach(r: Int)(f: HCen => Unit): Unit

  def rowMap[B, ArrB <: Arr[B]](r: Int)(f: HCen => B)(implicit build: BuilderArrMap[B, ArrB]): ArrB =
  { val len = rowLen(r)
    val res = build.uninitialised(len)
    var i = 0
    rowForeach(r){hc =>
      res(i) == f(hc)
      i += 1
    }
    res
  }

  override def coordCen: HCoord

  override def defaultView(pxScale: Double = 30): HGView = coordCen.view(pxScale)

  override def flatHCoordToPt2(hCoord: HCoord): Pt2 = hCoord.toPt2Reg

  /** The centre of the hex grid along the Y axis after the yRatio has been applied to the r row value. */
  final override def yCen: Double = rCen * yRatio

  /** foreachs over each [[HCen]] hex tile centre, applying the side effecting function. */
  final override def foreach(f: HCen => Unit): Unit = allRsforeach(r => rowForeach(r)(f))

  /** foreachs with index over each [[HCen]] hex tile centre, apply the side effecting function. */
  final override def iForeach(f: (Int, HCen) => Unit): Unit =
  { var i: Int = 0
    allRsforeach(r => rowForeach(r){hc => f(i, hc); i += 1 })
  }

  /** foreachs with index over each [[HCen]] hex tile centre, apply the side effecting function. */
  final override def iForeach(init: Int)(f: (Int, HCen) => Unit): Unit =
  { var i: Int = init
    allRsforeach(r => rowForeach(r){hc => f(i, hc); i += 1 })
  }

  /** Is the specified tile centre row empty? */
  final def cenRowEmpty(row: Int): Boolean = rowNumTiles(row) == 0

  /** The start (or by default left column) of the tile centre of the given row. */
  def rowLeftCenC(row: Int): Int

  /** The end (or by default right) column number of the tile centre of the given row. */
  def rowRightCenC(row: Int): Int

  /** The end (or by default right) column number of the hex coordinate row. So note that for the purposes of this method 2, 2 is not considered to be
   * in the same row as 2, 1 and 2, 3, although they have the same c number. Similarly Cen 2, 2 is not considered to be in the same row as sides 2, 0
   * and 2, 6. */
  def rowRightCoordC(row: Int, c: Int): Int = (row %% 4, c %% 4) match
  { //separators
    case (1, 3) | (3, 1) if row == topSepR => rowRightCenC(row - 1) - 2
    case (1, 1) | (3, 3) if row == topSepR => rowRightCenC(row - 1) + 2
    case (1, 3) | (3, 1) if row == bottomSepR => rowRightCenC(row + 1) + 2
    case (1, 1) | (3, 3) if row == bottomSepR => rowRightCenC(row + 1) - 2
    case (1, 3) | (3, 1) => (rowRightCenC(row + 1) + 2) max (rowRightCenC(row - 1) - 2)
    case (1, 1) | (3, 3) => (rowRightCenC(row + 1) - 2) max (rowRightCenC(row - 1) + 2)
    case (2, 0) | (0, 2) => rowRightCenC(row) + 2

    //centres
    case (0, 0) | (2, 2) => rowRightCenC(row)

    //others
    case (0 | 2, 1 | 3) => rowRightCenC(row)

    //verts
    case (1, 2) | (3, 0) if row == topSepR => rowRightCenC(row - 1) + 2
    case (1, 0) | (3, 2) if row == topSepR => rowRightCenC(row - 1)
    case (1, 2) | (3, 0) if row == bottomSepR => rowRightCenC(row + 1)
    case (1, 0) | (3, 2) if row == bottomSepR => rowRightCenC(row + 1) + 2
    case (1, 2) | (3, 0) => (rowRightCenC(row - 1) + 2) max rowRightCenC(row + 1)
    case _ => rowRightCenC(row - 1) max (rowRightCenC(row + 1) + 2)
  }

  def rowLeftCoordC(row: Int, c: Int): Int = (row %% 4, c %% 4) match
  { //Sides
    case (1, 3) | (3, 1) if row == topSepR => rowLeftCenC(row - 1) - 2
    case (1, 1) | (3, 3) if row == topSepR => rowLeftCenC(row - 1) + 2
    case (1, 3) | (3, 1) if row == bottomSepR => rowLeftCenC(row + 1) + 2
    case (1, 1) | (3, 3) if row == bottomSepR => rowLeftCenC(row + 1) - 2
    case (1, 3) | (3, 1) => (rowLeftCenC(row + 1) + 2) min (rowLeftCenC(row - 1) - 2)
    case (1, 1) | (3, 3) => (rowLeftCenC(row + 1) - 2) min (rowLeftCenC(row - 1) + 2)
    case (2, 0) | (0, 2) => rowLeftCenC(row) - 2

    //centres
    case (0, 0) | (2, 2) => rowLeftCenC(row)

    //others
    case (0 | 2, 1 | 3) => rowLeftCenC(row)

    //verts
    case (1, 2) | (3, 0) if row == topSepR => rowLeftCenC(row - 1) - 2
    case (1, 0) | (3, 2) if row == topSepR => rowLeftCenC(row - 1)
    case (1, 2) | (3, 0) if row == bottomSepR => rowLeftCenC(row + 1)
    case (1, 0) | (3, 2) if row == bottomSepR => rowLeftCenC(row + 1) - 2

    case (1, 2) | (3, 0) => (rowLeftCenC(row - 1) - 2) min rowLeftCenC(row + 1)
    case _ => rowLeftCenC(row - 1) min (rowLeftCenC(row + 1) - 2)
  }

  override def hSepExists(r: Int, c: Int): Boolean = None match
  { case _ if r > topSepR => false
    case _ if r < bottomSepR => false
    case _ if r.isEven => (c <= rowRightCenC(r) + 2) & (c >= rowLeftCenC(r) - 2)
    case _ if r == topSepR => c <= (rowRightCenC(r - 1) + 1) & c >= (rowLeftCenC(r - 1) - 1)
    case _ if r == bottomSepR => (c <= rowRightCenC(r + 1) + 1) & (c >= rowLeftCenC(r + 1) - 1)
    case _ if c > rowRightCenC(r - 1).max(rowRightCenC(r + 1)) + 1 => false
    case _ if c < rowLeftCenC(r - 1).min(rowLeftCenC(r + 1)) - 1 => false
    case _ => true
  }

  def hCoordExists(r: Int, c: Int): Boolean = None match
  { case _ if r > topSepR => false
    case _ if r < bottomSepR => false
    case _ if r.isEven => (c <= rowRightCenC(r) + 2) & (c >= rowLeftCenC(r) - 2)
    case _ if r == topSepR => c <= (rowRightCenC(r - 1) + 2) & c >= (rowLeftCenC(r - 1) - 2)
    case _ if r == bottomSepR  => (c <= rowRightCenC(r + 1) + 2) & (c >= rowLeftCenC(r + 1) - 2)
    case _ if c > rowRightCenC(r -1).max(rowRightCenC(r + 1)) + 2 => false
    case _ if c < rowLeftCenC(r - 1).min(rowLeftCenC(r + 1)) - 2 => false
    case _ => true
  }

  def rowVertHighLeftC(r: Int): Int =
  { val rUp = r + 1
    val rUpHC = rowLeftCenC(rUp)
    val rDn = r - 1
    val rDnHC = rowLeftCenC(rDn)
    ife(rUp %% 4 == 0, (rUpHC - 2).min(rDnHC), rUpHC.min(rDnHC - 2))
  }

  def rowVertLowLeftC (r: Int): Int =
  { val rUp = r + 1
    val rUpHC = rowLeftCenC(rUp)
    val rDn = r - 1
    val rDnHC = rowLeftCenC(rDn)
    ife(rUp %% 4 == 0, rUpHC.min(rDnHC - 2), (rUpHC - 2).min(rDnHC))
  }

  def rowVertHighRightC(r: Int): Int =
  { val rUp = r + 1
    val rUpHC = rowRightCenC(rUp)
    val rDn = r - 1
    val rDnHC = rowRightCenC(rDn)
    ife(rUp %% 4 == 0, (rUpHC + 2).max(rDnHC), rUpHC.min(rDnHC + 2))
  }

  def rowVertLowRightC(r: Int): Int =
  { val rUp = r + 1
    val rUpHC = rowRightCenC(rUp)
    val rDn = r - 1
    val rDnHC = rowRightCenC(rDn)
    ife(rUp %% 4 == 0, rUpHC.min(rDnHC + 2), (rUpHC + 2).min(rDnHC))
  }

  override def hCenSteps(hCen: HCen): HStepArr = HStep.full.filter(st => hCenExists(hCen.r + st.tr, hCen.c + st.tc))

  override def stepEndGet(startCen: HCen, step: HStep): HCen =
  { val endCen = HCen(startCen.r + step.tr, startCen.c + step.tc)
    if (hCenExists(endCen)) endCen else excep("Illegal end hex in unsafeStep method.")
  }

  override def stepEndFind(startHC: HCen, step: HStep): Option[HCen] =
  { val endHC = HCen(startHC.r + step.tr, startHC.c + step.tc)
    if (hCenExists(startHC) & hCenExists(endHC)) Some(endHC) else None
  }

  override def stepFind(startCen: HCen, endCen: HCen): Option[HStep] =
    ife(hCenExists(startCen) & hCenExists(endCen), hcSteps.optFind(_.hCenDelta == endCen - startCen), None)

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   *  departure and the tile of arrival. */
  def getHCost(startCen: HCen, endCen: HCen): Int =
  { val diff = endCen - startCen
    val c: Int = diff.c.abs
    val y: Int = diff.r.abs

    y - c match
    { case 0 => c
    case n if n > 0 => y
    case n if n %% 4 == 0 => y - n / 2 //Subtract because n is negative, y being greater than x
    case n => y - n / 2 + 2
    }
  }

  /** Implementation incomplete. */
  def rowCombine[A <: AnyRef](r: Int, layer: LayerHcRefSys[A], indexingGSys: HGridSys = this)(implicit ct2: ClassTag[A]): RPairArr[HCenRow, A] =
  { val buff: RPairBuff[HCenRow, A] = RPairBuff[HCenRow, A]()
    RPairArr.fromBuff(buff)
  }

  override def rowsCombine[A <: AnyRef](layer: LayerHcRefSys[A], indexingGSys: HGridSys = this): RArr[HCenRowPair[A]] =
  {
    allRsFlatMap[RArr[HCenRowPair[A]]]{ r =>
      if (cenRowEmpty(r)) RArr()
      else
      { var currStart: Int = rowLeftCenC(r)
        var currEnd: Int = rowRightCenC(r)
        var currC: Int = currStart
        var currVal: A = layer.rc(r, currStart)(indexingGSys)
        var list: List[HCenRowPair[A]] = Nil
        rowForeach(r){ hc =>
          currC = hc.c
          if (layer(hc)(indexingGSys) != currVal)
          { val newHCenRowValue = HCenRowPair(r, currStart, currC + 4, currVal)
            list :+= newHCenRowValue
            currVal = layer(hc)(indexingGSys)
            currStart = hc.c
          }
        }
        val newHCenRowValue = HCenRowPair(r, currStart, currC + 4, currVal)
        list :+= newHCenRowValue
        list.toArr
      }
    }
  }

  def outerPolygon: PolygonHC =
  { val buff = HCoordBuff()
    val tr = topCenR
    val tlc = rowLeftCenC(tr)
    val tlCen = HCen(tr, tlc)
    buff.grow(tlCen.v5)
    val trc = rowRightCenC(tr)
    iToForeach(tlc, trc, 4) { c =>
      val cen = HCen(tr, c)
      buff.grow(cen.v0)
      buff.grow(cen.v1)
    }

    var lastC = trc
    val br = bottomCenR
    iToForeach(tr - 2, br, -2){ currR =>
      val lastR = currR + 2
      val currC = rowRightCenC(currR)
      val step = ife(currC > lastC, 2, -2)
      iToForeach(lastC + 2, currC + 2, step){ c => buff.growInts(currR + 1, c) }
      lastC = currC
    }
    iToForeach(rowRightCenC(bottomCenR) + 2, rowLeftCenC(bottomCenR) - 2, -2){ c => buff.growInts(bottomSepR, c)}
    lastC = rowLeftCenC(bottomCenR)
    iToForeach(br + 2, tr, 2) { currR =>
      val lastR = currR - 2
      val currC = rowLeftCenC(currR)
      val step = ife(currC > lastC, 2, -2)
      iToForeach(lastC - 2, currC - 2, step) { c => buff.growInts(currR - 1, c) }
      lastC = currC
    }
    PolygonHC.fromBuff(buff)
  }

  /* Methods that operate on Hex tile sides. ******************************************************/

  /** foreach Hex side's coordinate HSide, calls the effectfull function.
   * @group SidesGroup */
  final override def sepsForeach(f: HSep => Unit): Unit = sepRowsForeach(r => rowForeachSep(r)(f))

  final def linksForeach(f: HSep => Unit): Unit = innerSepRowsForeach(r => innerRowForeachInnerSide(r)(f))

  def edgesForeach(f: HSep => Unit): Unit

  /** Calls the Foreach procedure on every hex separator [[HSep]] in the row given by the input parameter. */
  def rowForeachSep(r: Int)(f: HSep => Unit): Unit

  def rowNumSeps(r:Int): Int =
  { var i = 0
    rowForeachSep(r){ _ => i += 1}
    i
  }

  val sepIndexArr: Array[Int] =
  { val array = new Array[Int]((topSepR - bottomSepR + 1).max(2))
    array(0) = 0
    var i = 0
    var acc = 0
    iUntilForeach(bottomSepR, topSepR){ r =>
      i += 1
      acc += rowNumSeps(r)
      array(i) = acc
    }
    array
  }

  /** Array of indexs for separator data Arrs giving the index value for the start of each separator row. */
  lazy val sepRowIndexArray: Array[Int] =
  { val array = new Array[Int](numOfSepRows)
    var count = 0
    sepRowsForeach{ y =>
      array(y - bottomSepR) = count
      rowForeachSep(y)(_ => count += 1)
    }
    array
  }

  def topRowForeachSide(f: HSep => Unit): Unit =
    iToForeach(rowLeftCenC(topCenR) - 1, rowRightCenC(topCenR) + 1, 2){ c => f(HSep(topSepRow, c)) }

  def bottomRowForeachSide(f: HSep => Unit): Unit =
    iToForeach(rowLeftCenC(bottomCenR) - 1, rowRightCenC(bottomCenR) + 1, 2){ c => f(HSep(bottomSepR, c)) }

  def innerRowForeachInnerSide(r: Int)(f: HSep => Unit): Unit
  def newSideBooleans: HSideBoolLayer = new HSideBoolLayer(new Array[Boolean](numSides))

  def rowLeftSepC(r: Int): Int = r match
  { case r if r == topSepR => rowLeftCenC(topCenR) - 1
    case r if r == bottomSepR => rowLeftCenC(bottomCenR) - 1
    case r if r.isEven => rowLeftCenC(r) - 2
    case r => rowLeftCenC(r - 1).min(rowLeftCenC(r + 1)) - 1
  }

  def rowRightSepC(r: Int): Int = r match
  { case r if r == topSepR => rowRightCenC(topCenR) + 1
    case r if r == bottomSepR => rowRightCenC(bottomCenR) + 1
    case r if r.isEven => rowRightCenC(r) + 2
    case r => rowRightCenC(r - 1).max(rowRightCenC(r + 1)) + 1
  }

  /** Not correct yet. */
  override def findSepTiles(hs: HSep ): Option[(HCen, HCen)] = Some(hs.tileLtReg, hs.tileRtReg)

  override def sepTileLtUnsafe(hSide: HSep): HCen = hSide.tileLtReg

  override def sepTileRtUnsafe(hSide: HSep): HCen = hSide.tileRtReg

  override def sepTileRtOpt(hSide: HSep): Option[HCen] =
  { val ot: HCen = sepTileRtUnsafe(hSide)
    ife(hCenExists(ot), Some(ot), None)
  }

  /** Finds the [[HCoord]] if it exists, by taking the [[HVDirn]] from an [[HVert]]. */
  override def vertToCoordFind(hVert: HVert, dirn: HVDirn): Option[HCoord] =
  { val hc2: HCoord = hVert.dirnTo(dirn)
    ifSome(hCoordExists(hc2), hc2)
  }

  /** foreach Hex side's coordinate HSide, calls the effectfull function.
   * @group SidesGroup */
  final override def vertsForeach(f: HVert => Unit): Unit = vertRowsForeach(r => rowForeachVert(r)(f))

  /** Calls the Foreach procedure on every Hex vertex in the row given by the input parameter. */
  def rowForeachVert(r: Int)(f: HVert => Unit): Unit = iToForeach(rowLeftSepC(r) - 1, rowRightSepC(r) + 1, 2){ c => f(HVert(r, c)) }
}

object HGrid
{ /** Implicit [[Show]] type class instance / evidence for [[HGrid]]. */
  implicit lazy val showEv: ShowTellSum[HGrid] = ShowTellSum[HGrid]("HGrid")

  /** Implicit [[Unshow]] type class instance / evidence for [[HGrid]]. */
  implicit lazy val unshowEv: UnshowSum[HGrid] = UnshowSum[HGrid]("HGrid", HGridRect.unshowEv, HGridGen.unshowEv)
}

/** Hex grid path finding node. */
case class HNode(val tile: HCen, var gCost: Int, var hCost: Int, var parent: Option[HNode])
{ def fCost: Int = gCost + hCost
}