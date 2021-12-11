package connectfour



    fun main() {
        println("Connect Four")
        println("First player's name:")
        val firstPlayer = readLine()!!
        println("Second player's name:")
        val secondPlayer = readLine()!!
        var rows = 0
        var columns = 0
        while(true) {
            val boardSetting = "Set the board dimensions (Rows x Columns)\n" +
                    "Press Enter for default (6 x 7)"
            println(boardSetting)
            val input = readLine()!!.filter { !it.isWhitespace() }
            if (input == "") {
                rows = 6
                columns = 7
            } else {
                val regex = Regex("^[0-9]+(x|X)[0-9]+\$")
                if (!input.matches(regex)) {
                    println("Invalid Input")
                    continue
                } else {
                    rows = input.first().code - 48
                    if (rows < 5 || rows > 9) {
                        println("Board rows should be from 5 to 9")
                        continue
                    }
                    columns = input.last().code - 48
                    if (columns < 5 || columns > 9) {
                        println("Board columns should be from 5 to 9")
                        continue
                    }
                }
            }
            break
        }
        println("$firstPlayer VS $secondPlayer\n" +
                "$rows X $columns board")
        
        for(i in 0 until rows+2){
            for(j in 0..columns) {
                if(i==0){
                    if(j == columns){continue}
                    print(" ${j+1}")
                }
                else if(i==rows+1){
                    when (j) {
                        0 -> print("╚═")
                        columns -> print("╝")
                        else -> print("╩═")
                    }
                }
                else{print("║ ")}
            }
            print("\n")
        }


    }
