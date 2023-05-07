/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** A tile direction can be used for a tile step. */
trait TStepLike extends Any with Int1Elem
{/** Tile step r row coordinate delta. */
  def tr: Int

  /** Tile step c column coordinate delta */
  def tc: Int
}

/** A tile direction with a tile side. */
trait TStepSided extends TStepLike
{
  /** Tile half step r row coordinate delta. The r delta to the tile side. */
  def sr: Int

  /** Tile half step c column coordinate delta. The c delta to the tile side. */
  def sc: Int

  /** The delta in r for the target tile. */
  inline final override def tr: Int = sr * 2

  /** The delta in c for the target tile. */
  inline final override def tc: Int = sc * 2
}