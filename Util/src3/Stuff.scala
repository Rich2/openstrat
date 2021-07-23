package ostrat

def scala3Str(x: Int): String = "This is a Scala3 top level method. " + x.toString

object Top3
{
  def top3: String = "Top3"
}

export Top3.top3