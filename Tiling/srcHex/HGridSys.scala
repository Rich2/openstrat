/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag, pgui._

/** System of hex tile grids. Can be a single [[HGrid]] or a system of multiple hex tile grids. */
trait HGridSys extends HexStruct with TGridSys
{ /** Returns the most appropriate [[HSysProjection]] for this HGridSys. */
  def projection: Panel => HSysProjection = HSysProjectionFlat(this, _)

  final override lazy val numTiles: Int =
  { var i = 0
    foreach(_ => i += 1)
    i
  }

  /** The number of sides in the hex grid system. */
  final lazy val numSides: Int =
  { var i = 0
    sepsForeach(_ => i += 1)
    i
  }

  /** The number of inner sides in the hex grid system. */
  final lazy val numInnerSides: Int =
  { var i = 0
    linksForeach(_ => i += 1)
    i
  }

  /** The number of outer sides in the hex grid system. */
  final lazy val numOuterSides: Int =
  { var i = 0
    edgesForeach(_ => i += 1)
    i
  }

  def coordCen: HCoord
  def hCenSteps(hCen: HCen): HStepArr

  /** Unsafe. Gets the destination [[HCen]] from the [[HStep]]. Throws exception if no end point, */
  def stepEndGet(startCen: HCen, step: HStep): HCen

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  final def stepFind(startR: Int, startC: Int, endR: Int, endC: Int): Option[HStep] = stepFind(HCen(startR, startC), HCen(endR, endC))

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  def stepFind(startCen: HCen, endCen: HCen): Option[HStep]

  def stepExists(startCen: HCen, endCen: HCen): Boolean = stepFind(startCen, endCen).nonEmpty

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  def stepEndFind(startHC: HCen, step: HStep): Option[HCen]

  /** Optionally returns the destination of an [[HStep]] if the destination exists in the [[HGridSys]]. */
  def stepEndFind(r: Int, c: Int, step: HStep): Option[HCen] = stepEndFind(HCen(r, c), step)

  final def cenStepEndFind(cenStep: HCenStep): Option[HCen] = stepEndFind(cenStep.startHC, cenStep.step)

  def stepsEndFind(startCen: HCen, steps: HStepArr): Option[HCen] =
  {
    def loop(currCen: HCen, i: Int): Option[HCen] =
      if (i >= steps.length) Some(currCen)
      else stepEndFind(currCen, steps(i)) match
      { case None => None
        case Some(target) => loop(target, i + 1)
      }
    loop(startCen, 0)
  }

  /** Finds step from Start [[HCen]] to target from [[HCen]] if end hex exists, else returns start hex. */
  def stepEndOrStart(startHC: HCen, step: HStep): HCen = stepEndFind(startHC, step).getOrElse(startHC)

  /** Finds the end from an [[HStepLike]]. */
  def stepLikeEndFind(startHC: HCen, stepLike: HStepLike): Option[HCen] = stepLike match
  { case HStepStay => Some(startHC)
    case hs: HStep => stepEndFind(startHC, hs)
  }

  /** Gives a flat projection of [[HCoord]]s to [[Pt2]]s. For a simple singular [[HGrid]] system this is all that is required to translate between
   * grid coordinates and standard 2 dimensional space. For multi grids it provides a simple way to display all the tiles in the grid system, but a
   * more complex projection may be required for fully meaningful display representation. For Example world grid systems and multi layer square tile
   * games will require their own specialist projections. */
  def flatHCoordToPt2(hCoord: HCoord): Pt2

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   *  Array SeqDef data. */
  def layerArrayIndex(r: Int, c: Int): Int

  /** Gives the index into the unsafe backing [[Array]] of a [[HCornerLayer]]. */
  def cornerLayerArrayIndex(hc: HCen, vertIndex: Int): Int = layerArrayIndex(hc) * 6 + vertIndex

  /** For each row combine data layer into RArr[HCenRowPair]. May be superceded */
  def rowsCombine[A <: AnyRef](layer: LayerHcRefSys[A], indexingGSys: HGridSys): RArr[HCenRowPair[A]]

  /** Returns a clockwise sequence of adjacent tiles. */
  def adjTilesOfTile(origR: Int, origC: Int): HCenArr

  /** Returns a clockwise sequence of adjacent tiles. */
  final def adjTilesOfTile(origin: HCen): HCenArr = adjTilesOfTile(origin.r, origin.c)

  def sepTileRtOpt(hSide: HSep): Option[HCen]

  /** This method should only be used when you know the hex separator exists. */
  def sepTileRtUnsafe(hSide: HSep): HCen

  def sepTileLtOpt(hSide: HSep): Option[HCen]

  /** This method should only be used when you know the hex separator exists. */
  def sepTileLtUnsafe(hSide: HSep): HCen

  def sepTileLtAndVertUnsafe(hSide: HSep): (HCen, Int)

  def findPath(startCen: HCen, endCen: HCen)(fTerrCost: (HCen, HCen) => OptInt): Option[HCenArr] = findPathList(startCen, endCen)(fTerrCost).map(_.toArr)

  /** Finds path from Start hex tile centre to end tile centre given the cost function parameter. */
  def findPathList(startCen: HCen, endCen: HCen)(fTerrCost: (HCen, HCen) => OptInt): Option[List[HCen]] =
  {
    var open: List[HNode] = HNode(startCen, 0, getHCost(startCen, endCen), None) :: Nil
    var closed: List[HNode] = Nil
    var found: Option[HNode] = None

    while (open.nonEmpty & found == None)
    {
      val curr: HNode = open.minBy(_.fCost)
      open = open.filterNot(_ == curr)
      closed ::= curr
      val neighbs: HCenArr = adjTilesOfTile(curr.tile).filterNot(tile => closed.exists(_.tile == tile))
      neighbs.foreach { tile =>
        fTerrCost(curr.tile, tile) match {
          case NoInt =>
          case SomeInt(nc) if closed.exists(_.tile == tile) =>
          case SomeInt(nc) => {
            val newGCost = nc + curr.gCost

            open.find(_.tile == tile) match {
              case Some(node) if newGCost < node.gCost => node.gCost = newGCost; node.parent = Some(curr)
              case Some(_) =>
              case None =>
              { val newNode = HNode(tile, newGCost, getHCost(tile, endCen), Some(curr))
                open ::= newNode
                if (tile == endCen) found = Some(newNode)
              }
            }
          }
        }
      }
    }
    def loop(acc: List[HCen], curr: HNode): List[HCen] = curr.parent.fld(acc, loop(curr.tile :: acc, _))

    found.map(endNode =>  loop(Nil, endNode))
  }

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   *  departure and the tile of arrival. */
  def getHCost(startCen: HCen, endCen: HCen): Int

  /** Creates a new [[HCenBuffLayer]] An [[HCen] hex tile centre corresponding Arr of empty [[ArrayBuffer]]s of the given or inferred type. */
  final def newHCenArrOfBuff[A <: AnyRef](implicit ct: ClassTag[A]): HCenBuffLayer[A] = HCenBuffLayer(numTiles)

  /** Gives the index into an Arr / Array of Tile data from its tile [[HSep]]. Use arrIndex and vertIndex methods to access tile centre and Vertex
   *  Arr / Array data. */
  @inline final def sepLayerArrayIndex(hc: HSep): Int = sepLayerArrayIndex(hc.r, hc.c)

  /** Gives the index into an Arr / Array of side data from its tile [[HSep]]. Use arrIndex and vertIndex methods to access Side and Vertex Arr /
   *  Array data. */
  def sepLayerArrayIndex(r: Int, c: Int): Int

  /** foreach Hex separator's coordinate [[HSep]], calls the effectual function. */
  def sepsForeach(f: HSep => Unit): Unit

  /** foreach hex link / inner side's coordinate HSide, calls the effectual function. */
  def linksForeach(f: HSep => Unit): Unit

  /** foreach hex edge / outer side's coordinate HSide, calls the effectual function. */
  def edgesForeach(f: HSep => Unit): Unit

  /** maps over each Hex Separator's coordinate [[HSep]] in the hex grid system. */
  final def sepsMap[B, ArrT <: Arr[B]](f: HSep => B)(implicit build: BuilderArrMap[B, ArrT]): ArrT =
  { val res: ArrT = build.uninitialised(numSides)
    var i = 0
    sepsForeach{hs => res.setElemUnsafe(i, f(hs)); i += 1 }
    res
  }

  /** maps over each the grid systems link / inner side's coordinate [[HSep]]. */
  final def linksMap[B, ArrT <: Arr[B]](f: HSep => B)(implicit build: BuilderArrMap[B, ArrT]): ArrT =
  { val res: ArrT = build.uninitialised(numInnerSides)
    var i = 0
    linksForeach{ hs => res.setElemUnsafe(i, f(hs)); i += 1 }
    res
  }

  /** maps over each the grid systems outer side's coordinate [[HSep]]. */
  final def edgesMap[B, ArrT <: Arr[B]](f: HSep => B)(implicit build: BuilderArrMap[B, ArrT]): ArrT =
  { val res: ArrT = build.uninitialised(numOuterSides)
    var i = 0
    edgesForeach{ hs => res.setElemUnsafe(i, f(hs)); i += 1 }
    res
  }

  /** flatMaps over each Hex Separator's coordinate [[HSep]]. */
  final def sepsFlatMap[ArrT <: Arr[?]](f: HSep => ArrT)(implicit build: BuilderArrFlat[ArrT]): ArrT =
  { val buff = build.newBuff()
    sepsForeach{hs => build.buffGrowArr(buff, f(hs)) }
    build.buffToSeqLike(buff)
  }

  /** Optionally maps over each Hex Separator's coordinate [[HSep]]. */
  final def sepsOptMap[B, ArrB <: Arr[B]](f: HSep => Option[B])(implicit build: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    sepsForeach { hs => f(hs).foreach(b => build.buffGrow(buff, b)) }
    build.buffToSeqLike(buff)
  }

  /** OptMaps each [[HSep]] of this hex grid system to an [[HSepPair]]. */
  def sepOptMapPair[B2](f2: HSep => Option[B2])(implicit build: HSepBuilderArrPairMap[B2]): HSepArrPair[B2] =
  { val buff = build.newBuff()
    sepsForeach { hSide => f2(hSide).foreach { b2 => buff.pairGrow(hSide, b2) } }
    build.buffToSeqLike(buff)
  }

  /** flatMaps  over each inner hex Separator's coordinate [[HSep]].. */
  final def linksFlatMap[ArrT <: Arr[?]](f: HSep => ArrT)(implicit build: BuilderArrFlat[ArrT]): ArrT =
  { val buff = build.newBuff()
    linksForeach{ hs => build.buffGrowArr(buff, f(hs)) }
    build.buffToSeqLike(buff)
  }

  /** OptMaps over each inner hex Side's coordinate [[HSep]]. */
  final def linksOptMap[B, ArrB <: Arr[B]](f: HSep => Option[B])(implicit build: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    linksForeach { hs => f(hs).foreach(build.buffGrow(buff, _)) }
    build.buffToSeqLike(buff)
  }

  /** The [[HSep]] hex separator coordinates. */
  final def seps: HSepArr = sepsMap(hs => hs)

  /** Does the [[HSep]] hex tile separator exist within this[[HGridSys]]. */
  def sepExists(r: Int, c: Int): Boolean = sepExists(HSep(r, c))

  /** Does the [[HSep]] hex tile separator exist within this[[HGridSys]]. */
  def sepExists(hs: HSep): Boolean = hCenExists(hs.tileLtReg) | hCenExists(hs.tileRtReg)

  /** The line segments of the sides defined in [[HCoord]] vertices. */
  def sepLineSegHCs: LineSegHCArr = sepsMap(_.lineSegHC)

  /** The line segments of the inner separators defined in [[HCoord]] vertices. */
  def innerSepLineSegHCs: LineSegHCArr = linksMap(_.lineSegHC)

  /** The line segments of the outer separators defined in [[HCoord]] vertices. */
  def outerSepLineSegHCs: LineSegHCArr = edgesMap(_.lineSegHC)

  def defaultView(pxScale: Double = 30): HGView

  /** Gives the index into an Arr / Array of Tile data from its tile [[HVert]]. Use arrIndex and sideArrIndex methods to access tile centre and side
   *  Arr / Array data. */
  @inline final def vertArrIndex(hc: HSep): Int = vertArrIndex(hc.r, hc.c)

  /** Gives the index into an Arr / Array of side data from its tile [[HVert]]. Use arrIndex and vertIndex methods to access tile centre and side Arr
   *  / Array data. */
  def vertArrIndex(r: Int, c: Int): Int = 0

  def findSepTiles(hs: HSep ): Option[(HCen, HCen)] = ???

  /** Finds the [[HCoord]] if it exists, by taking the [[HVDirn]] from an [[HVert]]. */
  def vertToCoordFind(hVert: HVert, dirn: HVDirn): Option[HCoord]

  /** foreach Hex vertex coordinate [[HVert]], calls the effectual function. */
  def vertsForeach(f: HVert => Unit): Unit = ???

  /** maps over each Hex Separator's coordinate [[HSep]] in the hex grid system. */
  final def vertsMap[B, ArrT <: Arr[B]](f: HVert => B)(implicit build: BuilderArrMap[B, ArrT]): ArrT =
  { val res: ArrT = build.uninitialised(numSides)
    var i = 0
    vertsForeach{hs => res.setElemUnsafe(i, f(hs)); i += 1 }
    res
  }

  /** flatMaps over each Hex vertex's coordinate [[HVert]]. */
  final def vertsFlatMap[ArrT <: Arr[?]](f: HVert => ArrT)(implicit build: BuilderArrFlat[ArrT]): ArrT =
  { val buff = build.newBuff()
    vertsForeach{hs => build.buffGrowArr(buff, f(hs)) }
    build.buffToSeqLike(buff)
  }
}