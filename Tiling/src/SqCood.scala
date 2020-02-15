/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

/** I've kept this file because some code might be useful */
final class SqCoodDep(val x: Int, val y: Int)// extends TileCood
//{   
//   override def verts: Vec2s =  Vec2.xy(x - 1, y + 1,   x + 1, y + 1,   x + 1, y - 1,   x - 1, y -1)
//   def cenVerts: Vec2s =  Vec2.xy(0, 0, x - 1, y + 1,   x + 1, y + 1,   x + 1, y - 1,   x - 1, y -1)
//   def sideVerts: Vec2s = Unit match
//   {
//      case _ if (x %% 2 == 1) && (y %% 2 == 0) => Vec2.xy(x - 1, y, x + 1, y)
//      case _ if (x %% 2 == 0) && (y %% 2 == 1) => Vec2.xy(x, y - 1, x, y + 1)
//   }
//   def squareSideLine: Line2 = Unit match
//   {
//      case _ if (x %% 2 == 1) && (y %% 2 == 0) => Line2(x - 1, y, x + 1, y)
//      case _ if (x %% 2 == 0) && (y %% 2 == 1) => Line2(x, y - 1, x, y + 1)
//   }
//   
////   def getSquare(v: Vec2): SqCood =
////   {      
////      def dim(d: Double): Int = 
////      {
////         def at(ad: Double): Int = (ad / 2 + 0.5).toInt * 2
////         if (d < 0) - at(-d) else at(d)
////      }
////      SqCood(dim(v.x), dim(v.y))
////   }
//   
//}

//object SqCood extends TileCood
//{
//   override def toVec2(cood: Cood): Vec2 = Vec2(cood.x, (cood: Cood)y)
////   implicit object SqCoodPBuilder extends PBuilder2[SqCood, Int, Int]
////   {
////      override def persistType = "SqCood"
////      override def isType(obj: Any): Boolean = obj.isInstanceOf[SqCood]
////      override def apply = SqCood.apply  
////   }
//   
////   def getSquareCentresSymetrical(xSize: Int, ySize: Int): Seq[SqCood] = getSquareCentres(xSize * 2 + 1, ySize * 2 + 1, - xSize , - ySize) 
//   def squaresXByY(upperX: Int, upperY: Int): Seq[Cood] = getSquares(1, upperX * 2 - 1, 1, upperY * 2 - 1) 
//   def getSquares(lowerX: Int, upperX: Int, lowerY:Int, upperY: Int): Seq[Cood] =
//   {
//      val lx = lowerX.ifOdd(lowerX, lowerX + 1)
//      val ux = upperX.ifOdd(upperX, upperX - 1)
//      val ly = lowerY.ifOdd(lowerX, lowerX + 1)
//      val uy = upperY.ifOdd(upperX, upperX - 1)
//      getSquaresAdjusted(lx, ux, ly, uy)
//   }
//
//   @inline private[this] def getSquaresAdjusted(lowerX: Int, upperX: Int, lowerY:Int, upperY: Int): Seq[Cood] = for
//   {
//      x <- lowerX.ifOdd(lowerX, lowerX + 1) to upperX.ifOdd(upperX, upperX - 1) by 2
//      y <- lowerY.ifOdd(lowerX, lowerX + 1) to upperY.ifOdd(upperX, upperX - 1) by 2
//   } yield Cood(x, y)
//   def squaresWithSides(lowerX: Int, upperX: Int, lowerY:Int, upperY: Int): (Seq[Cood], Seq[Cood]) =
//   {
//      val lx = lowerX.ifOdd(lowerX, lowerX + 1)
//      val ux = upperX.ifOdd(upperX, upperX - 1)
//      val ly = lowerY.ifOdd(lowerX, lowerX + 1)
//      val uy = upperY.ifOdd(upperX, upperX - 1)
//      val cens: Seq[Cood] = getSquaresAdjusted(lx, ux, ly, uy)
//      val sides: Seq[Cood] = cens.ifEmpty(Seq(),
//         {
//            val s1 = for {x <- (lx - 1) to (ux + 1) by 2; y <- ly to uy by 2} yield Cood(x, y)
//            val s2 = for {x <- lx to ux by 2; y <- (ly - 1) to (uy + 1) by 2} yield Cood(x, y)            
//            s1 ++ s2
//         })
//      (cens, sides)
//   }
//   def squaresXByYWithSides(upperX: Int, upperY: Int): (Seq[Cood], Seq[Cood]) = squaresWithSides(1, upperX * 2 - 1, 1, upperY * 2 - 1) 
//   
//   implicit object GridBuilderSquare extends GridBuilder[Cood]
//   {
//      val xRatio: Double = 1
//      val xRadius: Double = 1
//      val yRadius: Double = 1
//   }     
//}

