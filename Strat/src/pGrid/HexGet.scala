/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

object HexGet
{
   /** Produces grid of hexs. The actual X coordinates will be multiplied by factor of 2 the y coordinates by factor of 2. if upperX is less than
   * lowerX or upperY is less than lower Y the Gird will be empty. If lowerX == upperX and lowerY  == UpperY and one of x and y are odd the grid will
   * be empty */
   def apply(lowerX: Int, upperX: Int, lowerY:Int, upperY: Int): Seq[Cood] =
   {      
      val (lxa, uxa, lya, uya) = row22Bounds(lowerX, upperX, lowerY, upperY)
      val a = for { x <- (lxa to uxa by 4); y <- (lya to uya by 4) } yield Cood(x, y)
      val (lxb, uxb, lyb, uyb) = row24Bounds(lowerX, upperX, lowerY, upperY)     
      val b = for { x <- (lxb to uxb by 4); y <- (lyb to uyb by 4) } yield Cood(x, y)
     a ++ b
   }
   def xByY(upperX: Int, upperY: Int): Seq[Cood] = apply(1, upperX * 2, 1, upperY * 2)
   
   /** Hex Grid rows for coordinates: -2, 2; 2; 2; 6, 2; -2, 6; 2, 6; 6, 6 */ 
   def row22Bounds(lowerX: Int, upperX: Int, lowerY:Int, upperY: Int): (Int, Int, Int, Int) =
   {
      val lx = lowerX.roundUpTo(i => (i - 2) %% 4 == 0)
      val ux = upperX.roundDownTo(i => (i - 2) %% 4 == 0)
      val ly = lowerY.roundUpTo(i => (i - 2) %% 4 == 0)
      val uy = upperY.roundDownTo(i => (i - 2) %% 4 == 0)
      (lx, ux, ly, uy)
   }
   
   /** Hex Grid rows for coordinates: -2, 4; 2; 4; 6, 4; -2, 8; 2, 8; 6, 8 */
   def row24Bounds(lowerX: Int, upperX: Int, lowerY:Int, upperY: Int): (Int, Int, Int, Int) =
   {
      val lx = lowerX.roundUpTo(i => i %% 4 == 0)
      val ux = upperX.roundDownTo(i => i %% 4 == 0)
      val ly = lowerY.roundUpTo(i => (i - 4) %% 4 == 0)
      val uy = upperY.roundDownTo(i => (i - 4) %% 4 == 0)
      (lx, ux, ly, uy)
   }
  
  def withSides(lowerX: Int, upperX: Int, lowerY:Int, upperY: Int): (Seq[Cood], Seq[Cood]) = ???
//  {
//     val (lxa, uxa, lya, uya) = row22Bounds(lowerX, upperX, lowerY, upperY)
//     val cen22 = for { x <- (lxa to uxa by 4); y <- (lya to uya by 4) } yield HexCood(x, y)
//     val (lxb, uxb, lyb, uyb) = row24Bounds(lowerX, upperX, lowerY, upperY)     
//     val cen24 = for { x <- (lxb to uxb by 4); y <- (lyb to uyb by 4) } yield HexCood(x, y)
//          
//     val s22l =  cen22.flatMap(c => Seq(c.subX(2), c.addXY(-1, -1), c.addXY(1, -1)))
//     val s22r = for {y <- lya to uya by 4 } yield HexCood(uxa + 2, y)
//     val s24l =  cen24.flatMap(c => Seq(c.subX(2), c.addXY(-1, -1), c.addXY(1, -1)))
//     val s24r = for {y <- lyb to uyb by 4 } yield HexCood(uxb + 2, y)
//     val s1 = s22l ++ s22r ++ s24l ++ s24r
//     
//     val s2 = cen22.ifEmpty(s1, 
//     {
//        val s3 = s1 ++ ife(
//              uya > uyb,
//              (lxa to uxa by 4).flatMap(x => Seq(HexCood(x - 1, uya + 1), HexCood(x + 1, uya + 1))),
//              Seq())
//        s3 ++ (lya to uya by 4).flatMap(y => Seq(HexCood(lxa - 1, y + 1), HexCood(uxa + 1, y + 1))).filterNot(i => s3.exists(_ == i))        
//     })
//     val sFinal = cen24.ifEmpty(s2, 
//     {
//        val s3 = s2 ++ ife(
//              uyb > uya,
//              (lxb to uxb by 4).flatMap(x => Seq(HexCood(x - 1, uyb + 1), HexCood(x + 1, uyb + 1))),
//              Seq())
//        s3 ++ (lyb to uyb by 4).flatMap(y => Seq(HexCood(lxb - 1, y + 1), HexCood(uxb + 1, y + 1))).filterNot(i => s3.exists(_ == i))        
//     })     
//     (cen22 ++ cen24, sFinal)
//  }
  
   def xByYWithSides(upperX: Int, upperY: Int): (Seq[Cood], Seq[Cood]) = ??? //withSides(1, upperX * 2, 1, upperY * 2)
   
   /** Alternative Hex Grid rows -2, 1; 2, 1; 6, 1; */
   def row21(lowerX: Int, upperX: Int, lowerY:Int, upperY: Int): (Int, Int, Int, Int) =
   {
      val lx = lowerX.roundUpTo(i => (i - 2) %% 4 == 0)
      val ux = upperX.roundDownTo(i => (i - 2) %% 4 == 0)
      val ly = lowerY.roundUpTo(i => (i - 1) %% 4 == 0)
      val uy = upperY.roundDownTo(i => (i - 1) %% 4 == 0)
      (lx, ux, ly, uy)
   }
   /** Alternative Hex Grid rows -2, 3; 2, 3; 6, 3; */
   def row23(lowerX: Int, upperX: Int, lowerY:Int, upperY: Int): (Int, Int, Int, Int) =
   {
      val lx = lowerX.roundUpTo(i => i %% 4 == 0)
      val ux = upperX.roundDownTo(i => i %% 4 == 0)
      val ly = lowerY.roundUpTo(i => (i - 3) %% 4 == 0)
      val uy = upperY.roundDownTo(i => (i - 3) %% 4 == 0)
      (lx, ux, ly, uy)
   }   
  
   private def altCentres(lowerX: Int, upperX: Int, lowerY:Int, upperY: Int):(Seq[Cood], Seq[Cood]) =
   {
      val (lxa, uxa, lya, uya) = row21(lowerX, upperX, lowerY, upperY)
      val a = for { x <- (lxa to uxa by 4); y <- (lya to uya by 4) } yield Cood(x, y)
      val (lxb, uxb, lyb, uyb) = row23(lowerX, upperX, lowerY, upperY)     
      val b = for { x <- (lxb to uxb by 4); y <- (lyb to uyb by 4) } yield Cood(x, y)     
      (a, b)
   }
   
   def altXByY(upperX: Int, upperY: Int): Seq[Cood] =
   {
      val p = altCentres(1, upperX, 1, upperY)
      p._1 ++ p._2
   }
   def row(y: Int, x1: Int, x2: Int): Seq[Cood] = y match
   {
      case y if y %% 4 == 2 => (x1.roundUpTo(_ %% 4 == 2) to x2.roundDownTo(_  %% 4 == 2) by 4).map(Cood(_, y))
      case y if y %% 4 == 0 => (x1.roundUpTo(_ %% 4 == 0) to x2.roundDownTo(_  %% 4 == 0) by 4).map(Cood(_, y))
   }
}
