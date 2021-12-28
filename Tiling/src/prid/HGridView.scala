/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

class HGridView(val r: Int, val c: Int, val pxScale: Double) extends Show2[HCoord, Double]
{
  def hCoord: HCoord = HCoord(r, c)
  def vec: Vec2 = hCoord.toVec
  def pt2: Pt2 = hCoord.toPt2
  override def typeStr: String = "GridView"
  override def name1: String = "hCoord"
  inline override def show1: HCoord = hCoord
  override def name2: String = "pxScale"
  inline override def show2: Double = pxScale
  override implicit def showT1: ShowT[HCoord] = HCoord.persistImplicit
  override implicit def showT2: ShowT[Double] = ShowT.doublePersistImplicit
  override def syntaxdepth: Int = 3
}

object HGridView
{
  def apply(r: Int, c: Int, pxScale: Double = 50): HGridView = new HGridView(r, c, pxScale)
  def apply(hCoord: HCoord, pxScale: Double): HGridView = new HGridView(hCoord.r, hCoord.c, pxScale)

  //implicit val persistImplicit: Persist2[HCoord, Double, HGridView] =
   // Persist2[HCoord, Double, HGridView]("HGridview", "hCoord")
}