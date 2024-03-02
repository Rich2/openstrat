/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

trait HexStruct extends TCenStruct
{
  /** The number of [[HCorner]]s in this [[HGridSys]], 6 for each [[HCen]]. */
  final def numCorners: Int = numTiles * 6

  /** The conversion factor for c column tile grid coordinates. 1.0 / sqrt(3). */
  final override def yRatio: Double = 3.sqrt

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   * Array SeqDef data. */
  def layerArrayIndex(r: Int, c: Int): Int

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   * data. */
  @inline final def layerArrayIndex(hc: HCen): Int = layerArrayIndex(hc.r, hc.c)

  /** Boolean. True if the [[HCen]] hex centre exists in this hex grid. */
  final def hCenExists(hc: HCen): Boolean = hCenExists(hc.r, hc.c)

  /** Boolean. True if the specified hex centre exists in this hex grid. */
  def hCenExists(r: Int, c:Int): Boolean

  /** If the given [[HCen]] exists within this [[HGridSys]], perform the side effecting function. */
  def hCenExistsIfDo(hc: HCen)(proc: => Unit): Unit = if(hCenExists(hc)) proc

  /** If the given [[HCen]] exists within this [[HGridSys]], perform the side effecting function. */
  def hCenExistsIfDo(r: Int, c: Int)(proc: => Unit): Unit = if(hCenExists(r, c)) proc

  /** Boolean. True if the [[HCen]] hex centre exists in this hex grid. */
  final def hCoordExists(hc: HCoord): Boolean = hCoordExists(hc.r, hc.c)

  /** Boolean. True if the specified hex centre exists in this hex grid. */
  def hCoordExists(r: Int, c:Int): Boolean

  /** foreachs over each [[HCen]] hex tile centre, applying the side effecting function. */
  def foreach(f: HCen => Unit): Unit

  /** foreachs with index over each [[HCen]] hex tile centre, apply the side effecting function. */
  def iForeach(f: (Int, HCen) => Unit): Unit

  /** foreachs with index over each [[HCen]] hex tile centre, apply the side effecting function. */
  def iForeach(init: Int)(f: (Int, HCen) => Unit): Unit

  /** Maps over the [[HCen]] hex centre tile coordinates. B is used rather than A as a type parameter, as this method maps from HCen => B,
   *  corresponding to the standard Scala map function of A => B. */
  final def map[B, ArrB <: Arr[B]](f: HCen => B)(implicit build: BuilderArrMap[B, ArrB]): ArrB =
  { val res = build.uninitialised(numTiles)
    iForeach((i, hCen) => res.setElemUnsafe(i, f(hCen)))
    res
  }

  /** Maps from all hex tile centre coordinates to an Arr of type ArrT. The elements of this array can not be accessed from this grid class as the
   * TileGrid structure is lost in the flatMap operation. */
  final def optMap[B, ArrB <: Arr[B]](f: HCen => Option[B])(implicit build: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = build.newBuff(numTiles)
    foreach { hCen => f(hCen).foreach(b => buff.grow(b)) }
    build.buffToSeqLike(buff)
  }

  /** Maps each [[Hcen]] to an element of type B, only if the predicate function on the [[HCen]] is true. Collects the true cases. In some cases this
   * will be easier and more efficient than employing the optMap method. */
  final def ifMap[B, ArrB <: Arr[B]](f1: HCen => Boolean)(f2: HCen => B)(implicit build: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = build.newBuff(numTiles)
    foreach { hCen => if(f1(hCen)) buff.grow(f2(hCen)) }
    build.buffToSeqLike(buff)
  }

  /** Maps each [[HCen]] of this hex grid system to an [[HCenPair]]. The first part of the pair is just the [[HCen]], the second part of the pair is
   * produced by the parameter function. */
  def mapPair[B2](f2: HCen => B2)(implicit build: HCenPairArrMapBuilder[B2]): HCenPairArr[B2] =
  { val res = build.uninitialised(numTiles)
    iForeach{ (i, hc) =>
      res.setA1Unsafe(i, hc)
      res.setA2Unsafe(i, f2(hc))
    }
    res
  }

  /** OptMaps each [[HCen]] of this hex grid system to an [[HCenPair]]. */
  def optMapPair[B2](f2: HCen => Option[B2])(implicit build: HCenPairArrMapBuilder[B2]): HCenPairArr[B2] =
  { val buff = build.newBuff()
    foreach{ hCen => f2(hCen).foreach{b2 => buff.pairGrow(hCen, b2) } }
    build.buffToSeqLike(buff)
  }

  /** flatMaps from all hex tile centre coordinates to an Arr of type ArrT. The elements of this array can not be accessed from this grid class as the
   *  TileGrid structure is lost in the flatMap operation. */
  final def flatMap[ArrT <: Arr[?]](f: HCen => ArrT)(implicit build: BuilderArrFlat[ArrT]): ArrT =
  { val buff = build.newBuff(numTiles)
    foreach{ hCen => build.buffGrowArr(buff, f(hCen))}
    build.buffToSeqLike(buff)
  }

  /** flatMaps from all hex tile centre coordinates to an Arr of type ArrT. The normal flatMap functions is only applied if the condtion of the first
   * function is true. */
  final def ifFlatMap[ArrT <: Arr[?]](f1: HCen => Boolean)(f2: HCen => ArrT)(implicit build: BuilderArrFlat[ArrT]): ArrT =
  { val buff = build.newBuff(numTiles)
    foreach { hCen => if(f1(hCen)) build.buffGrowArr(buff, f2(hCen)) }
    build.buffToSeqLike(buff)
  }

}