/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgiza
import geom.*, Colour.*

trait Pyramid
{
  def baseLen: Length
  def sideLen: Length = baseLen / 4
  def square = CircleLen2(sideLen / 2).fill(Wheat)
}

object GreatPyramid extends Pyramid
{
  def baseLen = 230.3.metres
  def height = 146.6.metres
}

object KhafrePyramid extends Pyramid
{
  override val baseLen = 215.25.metres
}

object MenkaurePyramid extends Pyramid
{
  override def baseLen: Length = 102.2.metres
}