/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgiza
import geom.*, Colour.*

trait Pyramid
{
  def sideLen: Length
  def square: SqlignLen2[?] = ???
  def squareFill = SqlignLen2Fill(SqlignLen2(sideLen).slate(offset), Wheat)
  def offset: VecLen2
  def offSquare = squareFill
  val axisOffsetNum = 300
}

object GreatPyramid extends Pyramid
{
  override def square: SqlignLen2[?] = ???
  override val sideLen = 230.3.metres
  def height = 146.6.metres

  override def offset: VecLen2 = VecM2(axisOffsetNum, axisOffsetNum)
}

object KhafrePyramid extends Pyramid
{
  override val sideLen = 215.25.metres
  override def offset: VecLen2 = VecM2(0, 0)
}

object MenkaurePyramid extends Pyramid
{
  override def sideLen: Length = 102.2.metres
  override def offset: VecLen2 = VecM2(-axisOffsetNum, -axisOffsetNum)
}

object Giza
{
  def pyramids = RArr(GreatPyramid, KhafrePyramid, MenkaurePyramid)
}