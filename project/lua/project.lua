-- Define User class
User = {}
User.__index = User
function User:create(firstname, lastname, username, password)
    local usr = {}
    setmetatable(usr, User)
    usr.firstname = firstname
    usr.lastname = lastname
    usr.username = username
    usr.password = password
end

function User:setFirstname()
    return self.firstname
end

function User:setLastname()
    return self.lastname
end

function User:setUsername()
    return self.username
end

function User:setPassword()
    return self.password
end

function User:getFirstname()
    return self.firstname
end

function User:getLastname()
    return self.lastname
end

function User:getUsername()
    return self.username
end

function User:getPassword()
    return self.password
end


-- ///////////////////////////////////////////////////////////////////////////////////////////





-- PROCESS MENU CODE
function processMenu(hashTable)
    local ynAnswer = ""
    local letterChoice = ""

    while true do
        print("What would you like to do with the User database? Select a letter from the list below")
        print("A. Insert a new User")
        print("B. Find an existing User")
        print("C. Remove an existing User")
        print("D. Undo removal of User")
        print("E. Print efficiency data")
        print("F. Quit program")
        letterChoice = io.read()

        if letterChoice == "a" or letterChoice == "A" then
            print("A")
        elseif letterChoice == "b" or letterChoice == "B" then
            print("B")
        elseif letterChoice == "c" or letterChoice == "C" then
            print("C")
        elseif letterChoice == "d" or letterChoice == "D" then
            print("D")
        elseif letterChoice == "e" or letterChoice == "E" then
            print("E")
        elseif letterChoice == "f" or letterChoice == "F" then 
            break
        else
            print("Letter not valid!")
        end 
end
end


-- MAIN CODE
local lines = {}

for line in io.lines("input.txt") do
    -- print(line)
    table.insert(lines, line)
end

local hashTable = {}

for i in pairs(lines) do
    -- print(lines[i])
    local tokens = {}
    for word in string.gmatch(lines[i], '([^,]+)') do
        -- print(word)
        table.insert(tokens, word)
    end
    -- print(tokens[0]) INDEX STARTS AT 1
    -- print(tokens[1])
    -- print(tokens[2])
    -- print(tokens[3])
    -- print(tokens[4])
    user = User:create{firstname = tokens[1], lastname = tokens[2], username = tokens[3], password = tokens[4]}
    hashTable[tokens[3]] = user 
end

processMenu(hashTable)






