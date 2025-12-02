let rec calculateSumForRange (current: string) (times: int) (minLimit: Int128) (maxLimit: Int128) (sum: Int128) (added: Int128 list) =
    let numberValue = Int128.Parse(String.replicate times current)
    match numberValue with
    | v when v > maxLimit -> sum, added
    | v when v < minLimit -> calculateSumForRange  (string (Int128.Parse current + Int128.Parse("1"))) times minLimit maxLimit sum added
    | v when List.contains numberValue added -> calculateSumForRange  (string (Int128.Parse current + Int128.Parse("1"))) times minLimit maxLimit sum added
    | v -> calculateSumForRange  (string (Int128.Parse current + Int128.Parse "1")) times minLimit maxLimit (sum + v) (numberValue::added)

let rec incrementIfPossible (firstValue: string) (times: int) (min: Int128) (max: Int128) (sum: Int128) (added: Int128 list)  = 
    if (Convert.ToString max).Length / times = 0
    then sum
    else 
        let firstElement = firstValue.Substring(0, firstValue.Length / times)
        let fe = 
            if firstElement = ""
            then "1"
            else firstElement
        let s, a = calculateSumForRange fe times min max sum added
        incrementIfPossible firstValue (times + 1) min max s a

let input = getInput() |> List.head

input.Split ","
    |> Array.toList
    |> List.fold(fun sum range ->
        let r = range.Split "-"
        let min = Int128.Parse r[0]
        let max = Int128.Parse r[1]
        incrementIfPossible r[0] 2 min max sum []
    ) (Int128.Parse "0")



    