/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid._, psq._
/** This is a package for Chess and Draughts (also known as Checkers). It has been included in the main Strat library, as useful to have code. Because
 *  they are finished games, with established rules and iconography. As opposed to applications like GOne, GTwo etc, which are super simple games
 *  merely created for the teaching of the use use of the ostrat libraries. And also as opposed to the games in the Dev module which are intended to
 *  be developed into interesting games which may at some point have their own individual repositories. */
package object pchess
{
  def uselessMethod: String = "Useless String"
  implicit class SqCenExtensions(thisSq: SqCen)
  {
    def checkeredColour(darkSquareColour: Colour, lightSquareColour: Colour): Colour =
      ife((thisSq.r + thisSq.c) %% 4 == 0, darkSquareColour, lightSquareColour)
  }
}
