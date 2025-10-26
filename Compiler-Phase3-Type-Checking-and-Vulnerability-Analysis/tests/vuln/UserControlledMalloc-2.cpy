void test_scanf():
    int n
    scanf("%d", &n)
    int* arr = malloc(n * sizeof(int))
    free(arr)
end

int main():
    test_scanf()
    return 0
end