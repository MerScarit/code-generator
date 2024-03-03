declare namespace API {
  type BaseResponseBoolean = {
    code?: number;
    data?: boolean;
    message?: string;
  };

  type BaseResponseInteger = {
    code?: number;
    data?: number;
    message?: string;
  };

  type BaseResponseLoginUserVO = {
    code?: number;
    data?: LoginUserVO;
    message?: string;
  };

  type BaseResponseLong = {
    code?: number;
    data?: string;
    message?: string;
  };

  type BaseResponsePagePostVO = {
    code?: number;
    data?: PagePostVO;
    message?: string;
  };

  type BaseResponsePageUser = {
    code?: number;
    data?: PageUser;
    message?: string;
  };

  type BaseResponsePageUserVO = {
    code?: number;
    data?: PageUserVO;
    message?: string;
  };

  type BaseResponsePostVO = {
    code?: number;
    data?: PostVO;
    message?: string;
  };

  type BaseResponseString = {
    code?: number;
    data?: string;
    message?: string;
  };

  type BaseResponseUser = {
    code?: number;
    data?: User;
    message?: string;
  };

  type BaseResponseUserVO = {
    code?: number;
    data?: UserVO;
    message?: string;
  };

  type checkParams = {
    timestamp: string;
    nonce: string;
    signature: string;
    echostr: string;
  };

  type DeleteRequest = {
    id?: string;
  };

  type getPostVOByIdParams = {
    id: string;
  };
  type FileConfig = {
    inputRootPath?: string;
    outputRootPath?: string;
    sourceRootPath?: string;
    type?: string;
    files?: FileInfo[];
  };

  type FileInfo = {
    inputPath?: string;
    outputPath?: string;
    type?: string;
    generateType?: string;
    condition?: string;
    groupKey?: string;
    groupName?: string;
  };

  type Generator = {
    id?: string;
    name?: string;
    description?: string;
    basePackage?: string;
    version?: string;
    author?: string;
    tags?: string;
    picture?: string;
    fileConfig?: string;
    modelConfig?: string;
    distPath?: string;
    status?: number;
    userId?: string;
    createTime?: string;
    updateTime?: string;
    isDelete?: number;
  };

  type GeneratorAddRequest = {
    name?: string;
    description?: string;
    basePackage?: string;
    version?: string;
    author?: string;
    tags?: string[];
    picture?: string;
    fileConfig?: FileConfig;
    modelConfig?: ModelConfig;
    distPath?: string;
    status?: number;
  };

  type GeneratorEditRequest = {
    id?: string;
    name?: string;
    description?: string;
    basePackage?: string;
    version?: string;
    author?: string;
    tags?: string[];
    picture?: string;
    fileConfig?: FileConfig;
    modelConfig?: ModelConfig;
    distPath?: string;
  };

  type GeneratorQueryRequest = {
    current?: number;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    id?: string;
    notId?: string;
    searchText?: string;
    tags?: string[];
    orTags?: string[];
    userId?: string;
    name?: string;
    description?: string;
    basePackage?: string;
    version?: string;
    author?: string;
    distPath?: string;
    status?: number;
  };

  type GeneratorUpdateRequest = {
    id?: string;
    name?: string;
    description?: string;
    basePackage?: string;
    version?: string;
    author?: string;
    tags?: string[];
    picture?: string;
    fileConfig?: FileConfig;
    modelConfig?: ModelConfig;
    distPath?: string;
    status?: number;
  };

  type GeneratorVO = {
    id?: string;
    name?: string;
    description?: string;
    basePackage?: string;
    version?: string;
    author?: string;
    tags?: string[];
    picture?: string;
    fileConfig?: FileConfig;
    modelConfig?: ModelConfig;
    distPath?: string;
    status?: number;
    userId?: string;
    createTime?: string;
    updateTime?: string;
    user?: UserVO;
  };

  type getGeneratorVOByIdParams = {
    id: string;
  };

  type getUserByIdParams = {
    id: string;
  };

  type getUserVOByIdParams = {
    id: string;
  };

  type LoginUserVO = {
    id?: string;
    userName?: string;
    userAvatar?: string;
    userProfile?: string;
    userRole?: string;
    createTime?: string;
    updateTime?: string;
  };

  type OrderItem = {
    column?: string;
    asc?: boolean;
  };

  type PagePostVO = {
    records?: PostVO[];
    total?: string;
    size?: string;
    current?: string;
    orders?: OrderItem[];
    optimizeCountSql?: boolean;
    searchCount?: boolean;
    optimizeJoinOfCountSql?: boolean;
    countId?: string;
    maxLimit?: string;
    pages?: string;
  };

  type PageUser = {
    records?: User[];
    total?: string;
    size?: string;
    current?: string;
    orders?: OrderItem[];
    optimizeCountSql?: boolean;
    searchCount?: boolean;
    optimizeJoinOfCountSql?: boolean;
    countId?: string;
    maxLimit?: string;
    pages?: string;
  };

  type PageUserVO = {
    records?: UserVO[];
    total?: string;
    size?: string;
    current?: string;
    orders?: OrderItem[];
    optimizeCountSql?: boolean;
    searchCount?: boolean;
    optimizeJoinOfCountSql?: boolean;
    countId?: string;
    maxLimit?: string;
    pages?: string;
  };

  type PostAddRequest = {
    title?: string;
    content?: string;
    tags?: string[];
  };

  type PostEditRequest = {
    id?: string;
    title?: string;
    content?: string;
    tags?: string[];
  };

  type PostFavourAddRequest = {
    postId?: string;
  };

  type PostFavourQueryRequest = {
    current?: string;
    pageSize?: string;
    sortField?: string;
    sortOrder?: string;
    postQueryRequest?: PostQueryRequest;
    userId?: string;
  };

  type PostQueryRequest = {
    current?: string;
    pageSize?: string;
    sortField?: string;
    sortOrder?: string;
    id?: string;
    notId?: string;
    searchText?: string;
    title?: string;
    content?: string;
    tags?: string[];
    orTags?: string[];
    userId?: string;
    favourUserId?: string;
  };

  type PostThumbAddRequest = {
    postId?: string;
  };

  type PostUpdateRequest = {
    id?: string;
    title?: string;
    content?: string;
    tags?: string[];
  };

  type PostVO = {
    id?: string;
    title?: string;
    content?: string;
    thumbNum?: number;
    favourNum?: number;
    userId?: string;
    createTime?: string;
    updateTime?: string;
    tagList?: string[];
    user?: UserVO;
    hasThumb?: boolean;
    hasFavour?: boolean;
  };

  type uploadFileParams = {
    uploadFileRequest: UploadFileRequest;
  };

  type UploadFileRequest = {
    biz?: string;
  };

  type User = {
    id?: string;
    userAccount?: string;
    userPassword?: string;
    unionId?: string;
    mpOpenId?: string;
    userName?: string;
    userAvatar?: string;
    userProfile?: string;
    userRole?: string;
    createTime?: string;
    updateTime?: string;
    isDelete?: number;
  };

  type UserAddRequest = {
    userName?: string;
    userAccount?: string;
    userAvatar?: string;
    userRole?: string;
  };

  type userLoginByWxOpenParams = {
    code: string;
  };

  type UserLoginRequest = {
    userAccount?: string;
    userPassword?: string;
  };

  type UserQueryRequest = {
    current?: string;
    pageSize?: string;
    sortField?: string;
    sortOrder?: string;
    id?: string;
    unionId?: string;
    mpOpenId?: string;
    userName?: string;
    userProfile?: string;
    userRole?: string;
  };

  type UserRegisterRequest = {
    userAccount?: string;
    userPassword?: string;
    checkPassword?: string;
  };

  type UserUpdateMyRequest = {
    userName?: string;
    userAvatar?: string;
    userProfile?: string;
  };

  type UserUpdateRequest = {
    id?: string;
    userName?: string;
    userAvatar?: string;
    userProfile?: string;
    userRole?: string;
  };

  type UserVO = {
    id?: string;
    userName?: string;
    userAvatar?: string;
    userProfile?: string;
    userRole?: string;
    createTime?: string;
  };
}
