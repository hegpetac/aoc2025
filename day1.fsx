getInput() |> List.fold(fun (actual, hits) rotation ->
    let value = System.Int32.Parse(rotation.Remove(0, 1)) 
    let newActual = 
        ((if rotation.StartsWith('R') 
        then actual + value
        else actual - value) % 100 + 100) % 100
    if newActual = 0 
    then newActual, hits + 1
    else newActual, hits
    
) (50, 0)  

let asd = [
    "L68";
    "L30";
    "R48";
    "L5";
    "R60";
    "L55";
    "L1";
    "L99";
    "R14";
    "L82"
    ]

getInput() |> List.fold(fun (actual, hits) rotation ->
    let value = System.Int32.Parse(rotation.Remove(0, 1))
    match rotation.StartsWith('R') with
    | true ->
        let resultOfRotation = actual + value
        resultOfRotation % 100, hits + resultOfRotation / 100
    | false ->
        let negativeSurplus = 
            if actual = 0
            then 0
            else 1
        let resultOfRotation = actual - value
        if resultOfRotation > 0 
        then resultOfRotation, hits
        else (resultOfRotation % 100 + 100) % 100, hits + Int32.Abs (resultOfRotation / 100) + negativeSurplus
) (50, 0)  
