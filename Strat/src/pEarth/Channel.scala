package ostrat
package pEarth
import pGrid._

object Channel
{
  import WTile._, RowMulti.{apply => rm}
  val v204463 = HVDnLt2(2, 2)
  val v206463 = HVUpRt2(2, 2)
  val v198463 = HVDown1(3)

  /*def apply(): MyGrid =
  {
    TGrid.rowMulti(460, MyGrid.apply,
      rm(468, 180, sea, hills * 3, plain * 3, sea * 2),
      rm(466, 178, sea, hills * 2, plain * 4, sea * 2),
      rm(464, 180, sea * 2, plain * 2, Land(dnRt = v198463), Land(dnLt = v198463), Land(dnRt = v206463, down = v204463, sideDR = Some(())),
        Coastal(dnLt = v206463), plain),
      rm(462, 178, sea , hills * 3, sea, Coastal(up = v198463), Coastal(upRt = v204463), Land(up = HVUpRt2(2, 2), upLt = v204463), plain),
      rm(460, 180, sea * 6, plain * 3),
    )
  }*/

  val rms: ArrOld[RowMulti[WTile]] = ArrOld(
    rm(468, 180, sea, hills * 3, plain * 3, sea * 2),
    rm(466, 178, sea, hills * 2, plain * 4, sea * 2),
    rm(464, 180, sea * 2, plain * 2, Land(dnRt = v198463), Land(dnLt = v198463), Land(dnRt = v206463, down = v204463, sideDR = Some(())),
      Coastal(dnLt = v206463), plain),
    rm(462, 178, sea , hills * 3, sea, Coastal(up = v198463), Coastal(upRt = v204463), Land(up = HVUpRt2(2, 2), upLt = v204463), plain),
    rm(460, 180, sea * 6, plain * 3),
  )
}

object ChannelApp extends App
{
  deb("This ChannelApp.")
}