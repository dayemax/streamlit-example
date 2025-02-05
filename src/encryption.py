# Writing Cipher Code
# Within the courses students are introduced to the following ciphers:
# • Caesar
# • Play Fair
# • Column Transportation
# • Vigenère
# For this assignment students are asked to write and test the code for any of the taught ciphers and
# one of the following
# • Hill
# • Alberti
# • Nihilist
# • Four-square
# • Atbash
# For each cipher the code should allow encryption as well as decryption.
# Students can choose any programming language but must inform the lecturer so that the
# appropriate compliers/assemblers are available for grading.


class Encryption():
    
    def playfairEncrypt(self, plain_text: str, key : str) -> str:
        key = key.lower()
        alphabet_list = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z']
        char_order = list(key)
        char_order.extend(alphabet_list)
        print(char_order)
        rows, cols = (5, 5)
        grid = [['']*cols]*rows
        char_set = set()
        i = 0
        for row in range(len(grid)):
            for col in range(len(grid)):
                if char_order[i] in char_set:
                    i+= 1
                    col -= 1
                    continue
                if char_order[i] == 'i' and 'j' in char_set:
                    i+= 1
                    col -= 1
                    continue
                if char_order[i] == 'j' and 'i' in char_set:
                    i+= 1
                    col -= 1
                    continue
                char_set.add(char_order[i])
                grid[row][col] = char_order[i]
        print(grid)

        # for char in char_order:
        #     if row >= len(grid):
        #         break
        #     if col >= len(grid[0]):
        #         col = 0
        #         row += 1
        #     if char in char_set:
        #         continue
        #     if char == 'i' and 'j' in char_set:
        #         continue
        #     if char == 'j' and 'i' in char_set:
        #         continue
        #     char_set.add(char)
        #     grid[col][row] = char
        #     col +=1
        # print(grid)





    def playfairDecrypt(encrypted_text: str, key: str) -> str:
        pass
        
trial1 = Encryption()
trial1.playfairEncrypt("I want candy", "Chocolate")