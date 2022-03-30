/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, Colour.Black, reflect.ClassTag

/** System of hex tile grids. Can be a single [[HGrid]] or a system of multiple hex tile grids. */
trait HGrider extends Any with TGrider
{
  /** The conversion factor for c column tile grid coordinates. 1.0 / sqrt(3). */
  final override def yRatio: Double = 3.sqrt

  /** Boolean. True if the [[HCen]] hex centre exists in this hex grid. */
  final def hCenExists(hc: HCen): Boolean = hCenExists(hc.r, hc.c)

  /** Boolean. True if the specified hex centre exists in this hex grid. */
  def hCenExists(r: Int, c:Int): Boolean

  def hCenSteps(hCen: HCen): HStepArr

  def unsafeStep(startCen: HCen, step: HStep): HCen

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  final def findStep(startR: Int, startC: Int, endR: Int, endC: Int): Option[HStep] = findStep(HCen(startR, startC), HCen(endR, endC))

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  def findStep(startHC: HCen, endHC: HCen): Option[HStep]

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  def findStepHC(startHC: HCen, step: HStep): Option[HCen]// = ???

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def arrIndex(hc: HCen): Int = arrIndex(hc.r, hc.c)

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   *  Array SeqDef data. */
  def arrIndex(r: Int, c: Int): Int

  def adjTilesOfTile(tile: HCen): HCens

  /** foreachs over each [[HCen]] hex tile centre, applying the side effecting function. */
  def foreach(f: HCen => Unit): Unit

  /** foreachs with index over each [[HCen]] hex tile centre, apply the side effecting function. */
  def iForeach(f: (HCen, Int) => Unit): Unit

  /** foreachs with index over each [[HCen]] hex tile centre, apply the side effecting function. */
  def iForeach(init: Int)(f: (HCen, Int) => Unit): Unit

  /** Creates a new [[HCenBuffDGrid]] An [[HCen] hex tile centre corresponding Arr of empty [[ArrayBuffer]]s of the given or inferred type. */
  final def newHCenArrOfBuff[A <: AnyRef](implicit ct: ClassTag[A]): HCenBuffDGrid[A] = HCenBuffDGrid(numTiles)

  /** Maps over the [[HCen]] hex centre tile coordinates. B is used rather than A as a type parameter, as this method maps from HCen => B,
   *  corresponding to the standard Scala map function of A => B. */
  final def map[B, ArrB <: SeqImut[B]](f: HCen => B)(implicit build: ArrBuilder[B, ArrB]): ArrB =
  { val res = build.newArr(numTiles)
    iForeach((hCen, i) => res.unsafeSetElem(i, f(hCen)))
    res
  }

  /** flatMaps from all hex tile centre coordinates to an Arr of type ArrT. The elements of this array can not be accessed from this grid class as the
   *  TileGrid structure is lost in the flatMap operation. */
  final def flatMap[ArrT <: SeqImut[_]](f: HCen => ArrT)(implicit build: ArrFlatBuilder[ArrT]): ArrT =
  { val buff = build.newBuff(numTiles)
    foreach{ hCen => build.buffGrowArr(buff, f(hCen))}
    build.buffToBB(buff)
  }

  /** New immutable Arr of Tile data. */
  final def newTileArr[A <: AnyRef](value: A)(implicit ct: ClassTag[A]): HCenDGrid[A] =
  { val res = HCenDGrid[A](numTiles)
    res.mutSetAll(value)
    res
  }

  /** New immutable Arr of Tile data. */
  final def newTileArrArr[A <: AnyRef](implicit ct: ClassTag[A]): HCenArrDGrid[A] =
  { val newArray = new Array[Array[A]](numTiles)
    val init: Array[A] = Array()
    iUntilForeach(0, numTiles)(newArray(_) = init)
    new HCenArrDGrid[A](newArray)
  }

  /** New Tile immutable Tile Arr of Opt data values. */
  final def newTileArrOpt[A <: AnyRef](implicit ct: ClassTag[A]): HCenOptDGrid[A] = new HCenOptDGrid(new Array[A](numTiles))

  /** The number of Sides in the TileGrid. Needs reimplementing.
   *  @group SidesGroup */
  def numSides: Int

  /** foreach Hex side's coordinate HSide, calls the effectfull function.
   * @group SidesGroup */
  def sidesForeach(f: HSide => Unit): Unit = sides.foreach(f)

  /** maps over each Hex Side's coordinate [[HSide]] in the given Row.
   *  @group SidesGroup */
  final def sidesMap[B, ArrT <: SeqImut[B]](f: HSide => B)(implicit build: ArrBuilder[B, ArrT]): ArrT =
  {
    val res: ArrT = build.newArr(numSides)
    var count = 0
    sidesForeach{hs =>
      res.unsafeSetElem(count, f(hs))
      count += 1
    }
    res
  }


  def sides: HSides
  def defaultView(pxScale: Double = 50): HGridView
}
